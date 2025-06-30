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
        List<Memo> memos = repository.find();

        return memos.stream()
                .map(MemoResponse::from)
                .toList();
    }
}
