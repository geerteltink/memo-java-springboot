package memo.application.findmemo;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.domain.MemoNotFound;
import memo.infrastructure.persistence.InMemoryMemoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMemoHandler {
    private final InMemoryMemoRepository repository;

    public MemoResponse handle(FindMemoQuery query) {
        Memo memo = this.repository.find(query.id());

        if (memo.deleted() != null) {
            throw new MemoNotFound(query.id());
        }

        return MemoResponse.from(memo);
    }
}
