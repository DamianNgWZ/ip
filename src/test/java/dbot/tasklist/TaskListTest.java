package dbot.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dbot.exception.DbotException;
import dbot.task.Deadline;
import dbot.task.Event;
import dbot.task.Task;
import dbot.task.Todo;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddTask() {
        Todo todo = new Todo("read book");
        taskList.add(todo);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testDeleteTask() throws DbotException {
        Todo todo = new Todo("read book");
        taskList.add(todo);

        Task deleted = taskList.delete(0);
        assertEquals(todo, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testDeleteInvalidIndex() {
        assertThrows(DbotException.class, () -> taskList.delete(5));
    }

    @Test
    public void testGetTask() throws DbotException {
        Todo todo = new Todo("read book");
        taskList.add(todo);

        Task retrieved = taskList.get(0);
        assertEquals(todo, retrieved);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(taskList.isEmpty());

        taskList.add(new Todo("read book"));
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void testFind() {
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("return book"));
        taskList.add(new Todo("buy milk"));

        List<Task> results = taskList.find("book");
        assertEquals(2, results.size());
    }

    @Test
    public void testFindNoMatch() {
        taskList.add(new Todo("read book"));

        List<Task> results = taskList.find("meeting");
        assertEquals(0, results.size());
    }

    @Test
    public void testSort() throws DbotException {
        // Add tasks in random order
        taskList.add(new Todo("read book"));
        taskList.add(new Deadline("submit report", LocalDate.of(2026, 2, 15)));
        taskList.add(new Event("meeting", LocalDate.of(2026, 2, 12), LocalDate.of(2026, 2, 12)));
        taskList.add(new Deadline("assignment", LocalDate.of(2026, 2, 10)));

        taskList.sort();

        // After sorting: earliest deadline first, then event, then later deadline, then todo
        assertEquals("[D][ ] assignment (by: Feb 10 2026)", taskList.get(0).toString());
        assertEquals("[E][ ] meeting (from: Feb 12 2026 to: Feb 12 2026)", taskList.get(1).toString());
        assertEquals("[D][ ] submit report (by: Feb 15 2026)", taskList.get(2).toString());
        assertEquals("[T][ ] read book", taskList.get(3).toString());
    }

    @Test
    public void testGetFormattedList() {
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("return book"));

        String formatted = taskList.getFormattedList();
        assertTrue(formatted.contains("1. [T][ ] read book"));
        assertTrue(formatted.contains("2. [T][ ] return book"));
    }
}
