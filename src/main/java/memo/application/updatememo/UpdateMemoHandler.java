package memo.application.updatememo;

import memo.application.MemoResponse;
import memo.domain.Memo;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UpdateMemoHandler {
    public MemoResponse handle(UpdateMemoCommand command) {
        Memo memo = new Memo(command.id(), "", null, null);

        memo.update(command.content(), Instant.now());

        return MemoResponse.from(memo);
    }
}
