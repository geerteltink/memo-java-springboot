package memo.application.creatememo;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.domain.Memo;
import memo.infrastructure.persistence.InMemoryMemoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemoHandler {
    private final InMemoryMemoRepository repository;

    public MemoResponse handle(CreateMemoCommand command) {
        Memo memo = Memo.create(command.id(), command.content());

        this.repository.insert(memo);

        return MemoResponse.from(memo);
    }
}
