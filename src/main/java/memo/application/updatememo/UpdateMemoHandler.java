package memo.application.updatememo;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.domain.MemoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UpdateMemoHandler {
    private final MemoRepository repository;

    public MemoResponse handle(UpdateMemoCommand command) {
        Memo memo = this.repository.find(command.id());
        memo.update(command.content(), Instant.now());

        this.repository.update(memo);

        return MemoResponse.from(memo);
    }
}
