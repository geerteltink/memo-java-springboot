package memo.application.creatememo;

import lombok.NonNull;

import java.util.UUID;

public record CreateMemoCommand(UUID id, @NonNull String content) {

}
