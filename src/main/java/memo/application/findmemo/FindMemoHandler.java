package memo.application.findmemo;

import memo.application.MemoResponse;
import memo.domain.Memo;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FindMemoHandler {
    public MemoResponse handle(FindMemoQuery query) {
        Memo memo = new Memo(query.id(), "# Sample Title\n\nSample content.", Instant.now(), Instant.now());

        return MemoResponse.from(memo);
    }
}
