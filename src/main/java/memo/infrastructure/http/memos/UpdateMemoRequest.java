package memo.infrastructure.http.memos;

import io.micrometer.common.lang.NonNull;

public record UpdateMemoRequest(@NonNull String content) {

}
