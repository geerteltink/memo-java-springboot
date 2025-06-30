package memo.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MemoTest {

    @Test
    void itCreatesMemoWithContentAndGeneratesTitle() {
        UUID id = UUID.randomUUID();
        String content = "# My Memo Title\nThis is the memo content.";
        Memo memo = Memo.create(id, content);

        assertEquals(id, memo.id());
        assertEquals("My Memo Title", memo.title());
        assertEquals(content, memo.content());
        assertNotNull(memo.created());
        assertNotNull(memo.modified());
        assertNull(memo.deleted());
    }

    @Test
    void itCreatesMemoWithUntitledIfContentIsEmpty() {
        Memo memo = Memo.create(UUID.randomUUID(), "");

        assertEquals("Untitled", memo.title());
    }

    @Test
    void itUpdatesMemoContentAndTitleAndModified() {
        Memo memo = Memo.create(UUID.randomUUID(), "Old content");
        final Instant beforeUpdate = memo.modified();

        String newContent = "# New Title\nUpdated content";
        Instant updateTime = Instant.now();

        memo.update(newContent, updateTime);

        assertEquals("New Title", memo.title());
        assertEquals(newContent, memo.content());
        assertEquals(updateTime, memo.modified());
        assertNull(memo.deleted());
        assertNotEquals(beforeUpdate, memo.modified());
    }

    @Test
    void itUpdatesMemoWithNullModifiedSetsNow() {
        Memo memo = Memo.create(UUID.randomUUID(), "Content");
        final Instant beforeUpdate = memo.modified();

        memo.update("Updated", null);

        assertNotNull(memo.modified());
        assertNotEquals(beforeUpdate, memo.modified());
    }

    @Test
    void itDeletesMemoAndSetsDeletedTimestamp() {
        Memo memo = Memo.create(UUID.randomUUID(), "Content");
        assertNull(memo.deleted());

        Instant deleteTime = Instant.now();
        memo.delete(deleteTime);

        assertEquals(deleteTime, memo.deleted());
    }

    @Test
    void itDeletesMemoWithNullTimestampSetsNow() {
        Memo memo = Memo.create(UUID.randomUUID(), "Content");
        assertNull(memo.deleted());

        memo.delete(null);

        assertNotNull(memo.deleted());
    }

    @Test
    void itRemovesDeletedFlagWhenUpdating() {
        Memo memo = Memo.create(UUID.randomUUID(), "Content");
        assertNotNull(memo.modified());
        assertNull(memo.deleted());

        memo.delete(null);
        assertNotNull(memo.deleted());

        memo.update("Updated", null);
        assertNull(memo.deleted());
    }

    @Test
    void itExtractsTitleFromContentHandlesNoMarkdownHeader() {
        Memo memo = Memo.create(UUID.randomUUID(), "Just a line of text");
        assertEquals("Just a line of text", memo.title());
    }

    @Test
    void itExtractsTitleFromContentHandlesMultipleHashes() {
        Memo memo = Memo.create(UUID.randomUUID(), "###   Heading Title   \nRest of content");
        assertEquals("Heading Title", memo.title());
    }

    @Test
    void itExtractsTitleFromContentHandlesWhitespaceTitle() {
        Memo memo = Memo.create(UUID.randomUUID(), "#    \nContent");
        assertEquals("Untitled", memo.title());
    }
}
