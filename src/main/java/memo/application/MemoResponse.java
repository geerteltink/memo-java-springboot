package memo.application;

import memo.domain.Memo;

import java.time.Instant;
import java.util.UUID;

public record MemoResponse(UUID id, String title, String content, Instant created, Instant modified, Instant deleted) {

    public static MemoResponse from(Memo memo) {
        return new MemoResponse(
                memo.id(),
                memo.title(),
                memo.content(),
                memo.created(),
                memo.modified(),
                memo.deleted());
    }
}
