package memo.infrastructure.persistence;

import memo.domain.Memo;
import memo.domain.MemoNotFound;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryMemoRepositoryTest {
    @Test
    void itCanInsertNewMemos() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "Test Memo");

        InMemoryMemoRepository repository = new InMemoryMemoRepository();
        repository.insert(memo);

        Memo foundMemo = repository.findById(id);
        assertNotNull(foundMemo);
        assertNotNull(foundMemo.id());
        assertNotNull(foundMemo.content());
        assertInstanceOf(Instant.class, foundMemo.created());
        assertInstanceOf(Instant.class, foundMemo.modified());
        assertNull(foundMemo.deleted());
    }

    @Test
    void testUpdate() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "Test Memo");

        InMemoryMemoRepository repository = new InMemoryMemoRepository();
        repository.insert(memo);

        Memo foundMemo = repository.findById(id);
        Memo clonedMemo = new Memo(foundMemo.id(), foundMemo.content(), foundMemo.created(), foundMemo.modified());
        clonedMemo.update("Updated Memo", Instant.now());
        repository.update(clonedMemo);

        Memo updatedMemo = repository.findById(id);

        assertSame(memo.id(), updatedMemo.id());
        assertSame(memo.created(), updatedMemo.created());
        assertNotSame(memo.title(), updatedMemo.title());
        assertNotSame(memo.content(), updatedMemo.content());
        assertNotSame(memo.modified(), updatedMemo.modified());
        assertNull(updatedMemo.deleted());
    }

    @Test
    void itCanDeleteMemos() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "Test Memo");

        InMemoryMemoRepository repository = new InMemoryMemoRepository();
        repository.insert(memo);
        repository.delete(memo);

        assertThrows(MemoNotFound.class, () -> repository.findById(id));
    }

    @Test
    void itFindsAndReturnsMemos() {
        UUID id = UUID.randomUUID();
        Memo memo = Memo.create(id, "Test Memo");

        InMemoryMemoRepository repository = new InMemoryMemoRepository();
        repository.insert(memo);

        Memo foundMemo = repository.findById(id);

        assertSame(memo, foundMemo);
    }

    @Test
    void itThrowsAnExceptionIfMemosAreNotFound() {
        UUID id = UUID.randomUUID();

        InMemoryMemoRepository repository = new InMemoryMemoRepository();

        assertThrows(MemoNotFound.class, () -> repository.findById(id));
    }
}
