package memo.application.deletememo;

import lombok.RequiredArgsConstructor;
import memo.domain.Memo;
import memo.domain.MemoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMemoHandler {
    private final MemoRepository repository;

    public void handle(DeleteMemoCommand command) {
        Memo memo = this.repository.findById(command.id());

        memo.delete(null);

        this.repository.update(memo);
    }
}
