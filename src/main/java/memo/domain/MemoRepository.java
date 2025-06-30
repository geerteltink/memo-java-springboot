package memo.domain;

import java.util.List;
import java.util.UUID;

public interface MemoRepository {
    public void insert(Memo memo);

    public void update(Memo memo);

    public void delete(Memo memo);

    /**
     * @throws Memofound if no memo is found for the given ID
     */
    public Memo findById(UUID id);

    public List<Memo> find();
}
