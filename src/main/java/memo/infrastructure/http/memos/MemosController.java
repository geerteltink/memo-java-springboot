package memo.infrastructure.http.memos;

import lombok.RequiredArgsConstructor;
import memo.application.MemoResponse;
import memo.application.creatememo.CreateMemoCommand;
import memo.application.creatememo.CreateMemoHandler;
import memo.application.deletememo.DeleteMemoCommand;
import memo.application.deletememo.DeleteMemoHandler;
import memo.application.findmemo.FindMemoHandler;
import memo.application.findmemo.FindMemoQuery;
import memo.application.findmemos.FindMemosHandler;
import memo.application.findmemos.FindMemosQuery;
import memo.application.updatememo.UpdateMemoCommand;
import memo.application.updatememo.UpdateMemoHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/memos")
@RequiredArgsConstructor
public class MemosController {
    private final FindMemosHandler findMemosHandler;
    private final CreateMemoHandler createMemoHandler;
    private final FindMemoHandler findMemoHandler;
    private final UpdateMemoHandler updateMemoHandler;
    private final DeleteMemoHandler deleteMemoHandler;

    @GetMapping
    public ResponseEntity<MemoResponse[]> findMemos() {
        MemoResponse[] response = findMemosHandler.handle(new FindMemosQuery(1, 1)).toArray(new MemoResponse[0]);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponse> findMemo(@PathVariable("id") UUID id) {
        MemoResponse response = findMemoHandler.handle(new FindMemoQuery(id));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MemoResponse> createMemo(@RequestBody CreateMemoRequest request) {
        MemoResponse response = createMemoHandler.handle(new CreateMemoCommand(request.content()));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponse> updateMemo(@PathVariable("id") UUID id,
            @RequestBody UpdateMemoRequest request) {
        MemoResponse response = updateMemoHandler.handle(new UpdateMemoCommand(id, request.content()));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable("id") UUID id) {
        deleteMemoHandler.handle(new DeleteMemoCommand(id));

        return ResponseEntity.noContent().build();
    }
}
