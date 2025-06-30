package memo.application.updatememo;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.infrastructure.persistence.InMemoryMemoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UpdateMemoHandler {
    private final InMemoryMemoRepository repository;

    public MemoResponse handle(UpdateMemoCommand command) {
        Memo memo = this.repository.find(command.id());
        memo.update(command.content(), Instant.now());

        this.repository.update(memo);

        return MemoResponse.from(memo);
    }
}
