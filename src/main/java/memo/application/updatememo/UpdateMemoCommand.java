package memo.application.updatememo;

import java.util.UUID;

public record UpdateMemoCommand(UUID id, String content) {

}
