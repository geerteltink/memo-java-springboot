package memo.application.findmemos;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.domain.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindMemosHandler {
    private final MemoRepository repository;

    public List<MemoResponse> handle(FindMemosQuery query) {
        return List.of(
                MemoResponse.from(Memo.create(null, "Sample Memo 1")),
                MemoResponse.from(Memo.create(null, "Sample Memo 2")));
    }

}
