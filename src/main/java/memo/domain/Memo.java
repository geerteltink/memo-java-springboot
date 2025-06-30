package memo.domain;

import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

public class Memo {
    private UUID id;
    private String title;
    private String content;
    private Instant created;
    private Instant modified;
    private Instant deleted;

    public Memo(@NonNull UUID id, @NonNull String content, Instant created, Instant modified) {
        this.id = id;
        this.title = this.extractTitleFromContent(content);
        this.content = content;
        this.created = created != null ? created : Instant.now();
        this.modified = modified != null ? modified : Instant.now();
    }

    public static Memo create(UUID id, @NonNull String content) {
        UUID memoId = (id != null) ? id : UUID.randomUUID();

        return new Memo(memoId, content, Instant.now(), Instant.now());
    }

    public void update(@NonNull String content, Instant modified) {
        this.title = this.extractTitleFromContent(content);
        this.content = content;
        this.modified = modified != null ? modified : Instant.now();
        this.deleted = null;
    }

    public void delete(Instant deleted) {
        this.deleted = deleted != null ? deleted : Instant.now();
    }

    private String extractTitleFromContent(String content) {
        if (content == null || content.isEmpty()) {
            return "Untitled";
        }

        String[] lines = content.split("\n", 2);
        String title = lines[0].replaceAll("^#+\\s*", "").trim();

        return title.isEmpty() ? "Untitled" : title;
    }

    public UUID id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

    public Instant created() {
        return created;
    }

    public Instant modified() {
        return modified;
    }

    public Instant deleted() {
        return deleted;
    }
}
