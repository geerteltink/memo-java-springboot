package memo.application.findmemo;

import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.domain.MemoNotFound;
import memo.domain.MemoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindMemoHandlerTest {
    @Mock
    private MemoRepository repository;

    private FindMemoHandler handler;

    @BeforeEach
    void setUp() {
        handler = new FindMemoHandler(repository);
    }

    @Test
    void itFindsAndReturnsMemos() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "# Title\nContent.");
        FindMemoQuery query = new FindMemoQuery(id);

        when(repository.find(id)).thenReturn(memo);

        MemoResponse response = handler.handle(query);

        verify(repository, times(1)).find(id);
        assertEquals(id, response.id());
        assertEquals("Title", response.title());
        assertEquals("# Title\nContent.", response.content());
    }

    @Test
    void itThrowsAnExceptionIfMemoIsNotFound() {
        UUID id = UUID.randomUUID();
        FindMemoQuery query = new FindMemoQuery(id);

        when(repository.find(id)).thenThrow(MemoNotFound.class);

        assertThrows(MemoNotFound.class, () -> handler.handle(query));

        verify(repository, times(1)).find(id);
    }
}
