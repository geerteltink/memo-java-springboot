package memo.application.creatememo;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.domain.Memo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemoHandler {

    public MemoResponse handle(CreateMemoCommand command) {
        Memo memo = Memo.create(command.id(), command.content());

        return MemoResponse.from(memo);
    }
}
