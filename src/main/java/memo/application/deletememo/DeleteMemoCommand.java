package memo.application.deletememo;

import lombok.NonNull;

import java.util.UUID;

public record DeleteMemoCommand(@NonNull UUID id) {

}
