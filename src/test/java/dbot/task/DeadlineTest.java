package dbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void constructor_newDeadline_isNotDone() {
        Deadline deadline = new Deadline("return book",
                LocalDate.of(2024, 12, 1));
        assertFalse(deadline.isDone());
    }

    @Test
    public void markAsDone_unmarkedDeadline_becomesMarked() {
        Deadline deadline = new Deadline("submit report",
                LocalDate.of(2024, 12, 15));
        deadline.markAsDone();
        assertTrue(deadline.isDone());
    }

    @Test
    public void markAsUndone_markedDeadline_becomesUnmarked() {
        Deadline deadline = new Deadline("submit report",
                LocalDate.of(2024, 12, 15));
        deadline.markAsDone();
        deadline.markAsUndone();
        assertFalse(deadline.isDone());
    }

    @Test
    public void toString_unmarkedDeadline_correctFormat() {
        Deadline deadline = new Deadline("return book",
                LocalDate.of(2024, 12, 1));
        assertEquals("[D][ ] return book (by: Dec 01 2024)", deadline.toString());
    }

    @Test
    public void toString_markedDeadline_correctFormat() {
        Deadline deadline = new Deadline("return book",
                LocalDate.of(2024, 12, 1));
        deadline.markAsDone();
        assertEquals("[D][X] return book (by: Dec 01 2024)", deadline.toString());
    }

    @Test
    public void toFileFormat_unmarkedDeadline_correctFormat() {
        Deadline deadline = new Deadline("submit assignment",
                LocalDate.of(2024, 11, 20));
        assertEquals("D | NOT DONE | submit assignment | 20-11-2024", deadline.toFileFormat());
    }

    @Test
    public void toFileFormat_markedDeadline_correctFormat() {
        Deadline deadline = new Deadline("submit assignment",
                LocalDate.of(2024, 11, 20));
        deadline.markAsDone();
        assertEquals("D | DONE | submit assignment | 20-11-2024", deadline.toFileFormat());
    }
}
