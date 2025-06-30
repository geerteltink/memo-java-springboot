package memo.application.findmemos;

import memo.application.MemoResponse;
import memo.domain.Memo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindMemosHandler {

    public List<MemoResponse> handle(FindMemosQuery query) {
        return List.of(
                MemoResponse.from(Memo.create(null, "Sample Memo 1")),
                MemoResponse.from(Memo.create(null, "Sample Memo 2")));
    }

}
