package memo.application.findmemos;

import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.domain.MemoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindMemosHandlerTest {
    @Mock
    private MemoRepository repository;

    private FindMemosHandler handler;

    @BeforeEach
    void setUp() {
        handler = new FindMemosHandler(repository);
    }

    @Test
    void itReturnsEmptyList() {
        FindMemosQuery query = new FindMemosQuery(1, 1);

        when(repository.find()).thenReturn(List.of());

        List<MemoResponse> response = handler.handle(query);

        verify(repository, times(1)).find();
        assertEquals(0, response.size());
    }

    @Test
    void itReturnsListWithMemos() {
        FindMemosQuery query = new FindMemosQuery(1, 1);

        Memo memo1 = Memo.create(null, "Memo 1");
        Memo memo2 = Memo.create(null, "Memo 2");
        Memo memo3 = Memo.create(null, "Memo 3");

        when(repository.find()).thenReturn(List.of(memo1, memo2, memo3));

        List<MemoResponse> response = handler.handle(query);

        verify(repository, times(1)).find();
        assertEquals(3, response.size());
    }
}
