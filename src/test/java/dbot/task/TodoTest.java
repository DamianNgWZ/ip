package dbot.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {
    @Test
    public void constructor_newTodo_isNotDone() {
        Todo todo = new Todo("buy milk");
        assertFalse(todo.isDone());
    }

    @Test
    public void markAsDone_unmarkedTodo_becomesMarked() {
        Todo todo = new Todo("buy milk");
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void markAsUndone_markedTodo_becomesUnmarked() {
        Todo todo = new Todo("buy milk");
        todo.markAsDone();
        todo.markAsUndone();
        assertFalse(todo.isDone());
    }

    @Test
    public void toString_unmarkedTodo_correctFormat() {
        Todo todo = new Todo("buy milk");
        assertEquals("[T][ ] buy milk", todo.toString());
    }

    @Test
    public void toString_markedTodo_correctFormat() {
        Todo todo = new Todo("buy milk");
        todo.markAsDone();
        assertEquals("[T][X] buy milk", todo.toString());
    }

    @Test
    public void getStatusIcon_unmarkedTodo_returnsSpace() {
        Todo todo = new Todo("read book");
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void getStatusIcon_markedTodo_returnsX() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }
}
