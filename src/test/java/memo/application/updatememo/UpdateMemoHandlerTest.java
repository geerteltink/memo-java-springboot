package memo.application.updatememo;

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
public class UpdateMemoHandlerTest {
    @Mock
    private MemoRepository repository;

    private UpdateMemoHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UpdateMemoHandler(repository);
    }

    @Test
    void itUpdatesMemos() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "# Title\nContent.");
        UpdateMemoCommand command = new UpdateMemoCommand(id, "# New Title\nNew content.");

        when(repository.findById(id)).thenReturn(memo);

        handler.handle(command);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).update(any(Memo.class));
    }

    @Test
    void itThrowsAnExceptionIfMemoIsNotFound() {
        UUID id = UUID.randomUUID();
        UpdateMemoCommand command = new UpdateMemoCommand(id, "# New Title\nNew content.");

        when(repository.findById(id)).thenThrow(MemoNotFound.class);

        assertThrows(MemoNotFound.class, () -> handler.handle(command));

        verify(repository, times(1)).findById(id);
        verify(repository, times(0)).update(any(Memo.class));
    }
}
