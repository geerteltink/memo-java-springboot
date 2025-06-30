package memo.application.creatememo;

import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.domain.MemoFound;
import memo.infrastructure.persistence.InMemoryMemoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateMemoHandlerTest {

    private InMemoryMemoRepository repository;
    private CreateMemoHandler handler;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMemoRepository();
        handler = new CreateMemoHandler(repository);
    }

    @Test
    void itCreatesMemoAndReturnsResponse() {
        UUID id = UUID.randomUUID();
        CreateMemoCommand command = new CreateMemoCommand(id, "Test content");

        MemoResponse response = handler.handle(command);

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Test content", response.content());
        Memo storedMemo = repository.find(id);
        assertNotNull(storedMemo);
        assertEquals("Test content", storedMemo.content());
    }

    @Test
    void itCreatesMemoWithEmptyContent() {
        UUID id = UUID.randomUUID();
        CreateMemoCommand command = new CreateMemoCommand(id, "");

        MemoResponse response = handler.handle(command);

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("", response.content());
        Memo storedMemo = repository.find(id);
        assertNotNull(storedMemo);
        assertEquals("", storedMemo.content());
    }

    @Test
    void itThrowsAnExceptionIfMemosAreFound() {
        UUID id = UUID.randomUUID();
        CreateMemoCommand command = new CreateMemoCommand(id, "Non-existent memo");

        handler.handle(command);
        assertThrows(MemoFound.class, () -> handler.handle(command));
    }
}
