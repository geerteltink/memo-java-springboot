package memo.application.deletememo;

import lombok.RequiredArgsConstructor;
import memo.domain.Memo;
import memo.infrastructure.persistence.InMemoryMemoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMemoHandler {
    private final InMemoryMemoRepository repository;

    public void handle(DeleteMemoCommand command) {
        Memo memo = this.repository.find(command.id());

        memo.delete(null);

        this.repository.update(memo);
    }
}
