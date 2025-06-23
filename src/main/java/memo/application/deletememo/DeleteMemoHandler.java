package memo.application.deletememo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMemoHandler {
    public void handle(DeleteMemoCommand command) {
        // Logic to delete a memo by its ID
        // This could involve calling a repository or service to perform the deletion
        System.out.println("Deleting memo with ID: " + command.id());
        // Here you would typically call a repository method to delete the memo
    }
}
