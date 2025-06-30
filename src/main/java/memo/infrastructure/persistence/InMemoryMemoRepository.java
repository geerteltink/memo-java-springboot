package memo.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import memo.domain.Memo;
import memo.domain.MemoFound;
import memo.domain.MemoNotFound;
import memo.domain.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InMemoryMemoRepository implements MemoRepository {
    private final Map<UUID, Memo> memos = new HashMap<>();

    @Override
    public void insert(Memo memo) {
        if (this.memos.containsKey(memo.id())) {
            throw new MemoFound(memo.id());
        }

        this.memos.put(memo.id(), memo);
    }

    @Override
    public void update(Memo memo) {
        if (!this.memos.containsKey(memo.id())) {
            throw new MemoNotFound(memo.id());
        }

        this.memos.replace(memo.id(), memo);
    }

    @Override
    public void delete(Memo memo) {
        this.memos.remove(memo.id());
    }

    @Override
    public Memo find(UUID id) {
        Memo memo = this.memos.get(id);
        if (memo == null) {
            throw new MemoNotFound(id);
        }

        return memo;
    }
}
