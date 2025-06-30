package memo.infrastructure.http.memos;

import memo.application.MemoResponse;
import memo.application.creatememo.CreateMemoCommand;
import memo.application.creatememo.CreateMemoHandler;
import memo.application.deletememo.DeleteMemoCommand;
import memo.application.deletememo.DeleteMemoHandler;
import memo.application.findmemo.FindMemoHandler;
import memo.application.findmemo.FindMemoQuery;
import memo.application.findmemos.FindMemosHandler;
import memo.application.findmemos.FindMemosQuery;
import memo.application.updatememo.UpdateMemoCommand;
import memo.application.updatememo.UpdateMemoHandler;
import memo.domain.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MemosControllerTest {

    private FindMemosHandler findMemosHandler;
    private CreateMemoHandler createMemoHandler;
    private FindMemoHandler findMemoHandler;
    private UpdateMemoHandler updateMemoHandler;
    private DeleteMemoHandler deleteMemoHandler;
    private MemosController controller;

    @BeforeEach
    void setUp() {
        findMemosHandler = mock(FindMemosHandler.class);
        createMemoHandler = mock(CreateMemoHandler.class);
        findMemoHandler = mock(FindMemoHandler.class);
        updateMemoHandler = mock(UpdateMemoHandler.class);
        deleteMemoHandler = mock(DeleteMemoHandler.class);
        controller = new MemosController(
                findMemosHandler,
                createMemoHandler,
                findMemoHandler,
                updateMemoHandler,
                deleteMemoHandler);
    }

    @Test
    void testFindMemos() {
        MemoResponse memo1 = MemoResponse.from(Memo.create(UUID.randomUUID(), "content1"));
        MemoResponse memo2 = MemoResponse.from(Memo.create(UUID.randomUUID(), "content2"));
        when(findMemosHandler.handle(any(FindMemosQuery.class)))
                .thenReturn(java.util.List.of(memo1, memo2));

        ResponseEntity<MemoResponse[]> response = controller.findMemos();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        verify(findMemosHandler).handle(any(FindMemosQuery.class));
    }

    @Test
    void testFindMemo() {
        UUID id = UUID.randomUUID();
        MemoResponse memo = MemoResponse.from(Memo.create(id, "test content"));
        when(findMemoHandler.handle(any(FindMemoQuery.class))).thenReturn(memo);

        ResponseEntity<MemoResponse> response = controller.findMemo(id);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        verify(findMemoHandler).handle(any(FindMemoQuery.class));
    }

    @Test
    void testCreateMemo() {
        UUID id = UUID.randomUUID();
        String content = "new memo";
        CreateMemoRequest request = new CreateMemoRequest(id, content);
        MemoResponse memo = MemoResponse.from(Memo.create(id, content));
        when(createMemoHandler.handle(any(CreateMemoCommand.class))).thenReturn(memo);

        ResponseEntity<MemoResponse> response = controller.createMemo(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        assertEquals(content, response.getBody().content());
        ArgumentCaptor<CreateMemoCommand> captor = ArgumentCaptor.forClass(CreateMemoCommand.class);
        verify(createMemoHandler).handle(captor.capture());
        assertEquals(id, captor.getValue().id());
        assertEquals(content, captor.getValue().content());
    }

    @Test
    void testUpdateMemo() {
        UUID id = UUID.randomUUID();
        String updatedContent = "updated content";
        UpdateMemoRequest request = new UpdateMemoRequest(updatedContent);
        MemoResponse memo = MemoResponse.from(Memo.create(id, updatedContent));
        when(updateMemoHandler.handle(any(UpdateMemoCommand.class))).thenReturn(memo);

        ResponseEntity<MemoResponse> response = controller.updateMemo(id, request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        assertEquals(updatedContent, response.getBody().content());
        ArgumentCaptor<UpdateMemoCommand> captor = ArgumentCaptor.forClass(UpdateMemoCommand.class);
        verify(updateMemoHandler).handle(captor.capture());
        assertEquals(id, captor.getValue().id());
        assertEquals(updatedContent, captor.getValue().content());
    }

    @Test
    void testDeleteMemo() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = controller.deleteMemo(id);

        assertEquals(204, response.getStatusCode().value());
        verify(deleteMemoHandler).handle(any(DeleteMemoCommand.class));
    }
}
