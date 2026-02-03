package dbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void constructor_newEvent_isNotDone() {
        Event event = new Event("project meeting",
                LocalDate.of(2024, 10, 15),
                LocalDate.of(2024, 10, 15));
        assertFalse(event.isDone());
    }

    @Test
    public void markAsDone_unmarkedEvent_becomesMarked() {
        Event event = new Event("team dinner",
                LocalDate.of(2024, 11, 10),
                LocalDate.of(2024, 11, 10));
        event.markAsDone();
        assertTrue(event.isDone());
    }

    @Test
    public void markAsUndone_markedEvent_becomesUnmarked() {
        Event event = new Event("team dinner",
                LocalDate.of(2024, 11, 10),
                LocalDate.of(2024, 11, 10));
        event.markAsDone();
        event.markAsUndone();
        assertFalse(event.isDone());
    }

    @Test
    public void toString_unmarkedEvent_correctFormat() {
        Event event = new Event("project meeting",
                LocalDate.of(2024, 10, 15),
                LocalDate.of(2024, 10, 16));
        assertEquals("[E][ ] project meeting (from: Oct 15 2024 to: Oct 16 2024)", event.toString());
    }

    @Test
    public void toString_markedEvent_correctFormat() {
        Event event = new Event("project meeting",
                LocalDate.of(2024, 10, 15),
                LocalDate.of(2024, 10, 16));
        event.markAsDone();
        assertEquals("[E][X] project meeting (from: Oct 15 2024 to: Oct 16 2024)", event.toString());
    }

    @Test
    public void toFileFormat_unmarkedEvent_correctFormat() {
        Event event = new Event("conference",
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 9, 3));
        assertEquals("E | NOT DONE | conference | 01-09-2024 | 03-09-2024", event.toFileFormat());
    }

    @Test
    public void toFileFormat_markedEvent_correctFormat() {
        Event event = new Event("conference",
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 9, 3));
        event.markAsDone();
        assertEquals("E | DONE | conference | 01-09-2024 | 03-09-2024", event.toFileFormat());
    }
}
