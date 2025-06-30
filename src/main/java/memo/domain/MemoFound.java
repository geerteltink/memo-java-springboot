package memo.domain;

import java.util.UUID;

public class MemoFound extends RuntimeException {
    public MemoFound(UUID id) {
        super("Memo exists for id: " + id);
    }
}
