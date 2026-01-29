package dbot.tasklist;

import java.util.ArrayList;
import java.util.List;
import dbot.exception.DbotException;
import dbot.task.Task;

/**
 * Manages a list of tasks.
 * Provides operations to add, delete, retrieve, and format tasks for display.
 */
public class TaskList {
    /** The internal list storing all tasks. */
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param index The zero-based index of the task to delete.
     * @return The deleted task.
     * @throws DbotException If the index is out of range.
     */
    public Task delete(int index) throws DbotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DbotException("OOPS!!! dbot.task.Task number does not exist.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The task at the specified index.
     * @throws DbotException If the index is out of range.
     */
    public Task get(int index) throws DbotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DbotException("OOPS!!! dbot.task.Task number does not exist.");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Finds all tasks that contain the given keyword in their description.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return A list of tasks that match the keyword.
     */
    public List<Task> find(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Returns whether the task list is empty.
     *
     * @return true if the task list contains no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the internal list of tasks.
     * Used for saving tasks to storage.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a formatted string representation of all tasks for display.
     * Each task is numbered starting from 1.
     *
     * @return A formatted string containing all tasks, or an empty string if the list is empty.
     */
    public String getFormattedList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }
}
