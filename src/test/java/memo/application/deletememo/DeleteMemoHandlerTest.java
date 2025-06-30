package memo.application.deletememo;

import memo.domain.Memo;
import memo.domain.MemoNotFound;
import memo.domain.MemoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteMemoHandlerTest {
    @Mock
    private MemoRepository repository;

    private DeleteMemoHandler handler;

    @BeforeEach
    void setUp() {
        handler = new DeleteMemoHandler(repository);
    }

    @Test
    void itHandlesMemoDeletion() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "");
        DeleteMemoCommand command = new DeleteMemoCommand(id);

        when(repository.find(id)).thenReturn(memo);

        handler.handle(command);

        verify(repository, times(1)).find(id);
        verify(repository, times(1)).update(any(Memo.class));
    }

    @Test
    void itThrowsAnExceptionIfMemoIsNotFound() {
        UUID id = UUID.randomUUID();
        DeleteMemoCommand command = new DeleteMemoCommand(id);

        when(repository.find(id)).thenThrow(MemoNotFound.class);

        assertThrows(MemoNotFound.class, () -> handler.handle(command));

        verify(repository, times(1)).find(id);
        verify(repository, times(0)).update(any(Memo.class));
    }
}
