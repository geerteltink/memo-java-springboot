package memo.infrastructure.http;

import memo.domain.MemoFound;
import memo.domain.MemoNotFound;
import memo.domain.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ErrorHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemoRepository repository;

    @Test
    void shouldReturnNotFoundWhenMemoNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenThrow(new MemoNotFound(id));

        mockMvc.perform(get("/memos/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").value("https://httpstatus.es/404"))
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Memo not found for id: " + id));
    }

    @Test
    void shouldReturnBadRequestWhenMemoFoundOnCreate() throws Exception {
        UUID id = UUID.randomUUID();
        String requestBody = """
                {
                    "id": "%s",
                    "content": "Test content"
                }
                """.formatted(UUID.randomUUID());

        doThrow(new MemoFound(id)).when(repository).insert(any());

        mockMvc.perform(post("/memos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").value("https://httpstatus.es/400"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Memo exists for id: " + id));
    }

    @Test
    void shouldReturnBadRequestForValidationErrors() throws Exception {
        String invalidRequestBody = """
                {
                    "id": "invalid-uuid",
                    "content": ""
                }
                """;

        mockMvc.perform(post("/memos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnNotFoundForInvalidEndpoint() throws Exception {
        mockMvc.perform(get("/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnMethodNotAllowedForUnsupportedMethod() throws Exception {
        mockMvc.perform(delete("/memos"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(405));
    }

    @Test
    void shouldReturnBadRequestForTypeMismatch() throws Exception {
        mockMvc.perform(get("/memos/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnInternalServerErrorForRuntimeException() throws Exception {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/memos/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.detail").value("Internal Server Error"));
    }
}
