package memo.application.updatememo;

import lombok.NonNull;

import java.util.UUID;

public record UpdateMemoCommand(@NonNull UUID id, @NonNull String content) {

}
