package dbot.tasklist;

import java.util.ArrayList;
import java.util.List;
import dbot.exception.DbotException;
import dbot.task.Task;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws DbotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DbotException("OOPS!!! dbot.task.Task number does not exist.");
        }
        return tasks.remove(index);
    }

    public Task get(int index) throws DbotException {
        if (index < 0 || index >= tasks.size()) {
            throw new DbotException("OOPS!!! dbot.task.Task number does not exist.");
        }
        return tasks.get(index);
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

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getFormattedList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }
}
