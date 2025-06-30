package memo.infrastructure.http.memos;

import lombok.NonNull;

import java.util.UUID;

public record CreateMemoRequest(UUID id, @NonNull String content) {

}
