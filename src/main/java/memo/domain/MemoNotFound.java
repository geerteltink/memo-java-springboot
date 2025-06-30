package memo.domain;

import java.util.UUID;

public class MemoNotFound extends RuntimeException {
    public MemoNotFound(UUID id) {
        super("Memo not found for id: " + id);
    }
}
