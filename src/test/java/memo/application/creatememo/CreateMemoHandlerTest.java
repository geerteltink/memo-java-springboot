package memo.application.creatememo;

import memo.domain.Memo;
import memo.domain.MemoFound;
import memo.domain.MemoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateMemoHandlerTest {

    @Mock
    private MemoRepository repository;

    private CreateMemoHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreateMemoHandler(repository);
    }

    @Test
    void itHandlesMemoCreation() {
        UUID id = UUID.randomUUID();
        CreateMemoCommand command = new CreateMemoCommand(id, "Test content");

        handler.handle(command);

        verify(repository, times(1)).insert(any(Memo.class));
    }

    @Test
    void itThrowsAnExceptionIfMemoIsFound() {
        UUID id = UUID.randomUUID();
        CreateMemoCommand command = new CreateMemoCommand(id, "Test content");

        doThrow(MemoFound.class).when(repository).insert(any(Memo.class));

        assertThrows(MemoFound.class, () -> handler.handle(command));
    }
}
