package dbot.task;

/**
 * Represents a task in the task list.
 * A Task has a description and a completion status.
 * This is an abstract class that should be extended by specific task types
 * such as Todo, Deadline, and Event.
 */
public abstract class Task {
    /** The description of this task. */
    protected String description;

    /** The completion status of this task. */
    protected boolean isDone;

    /**
     * Returns the string representation of this task for saving to a file.
     * The format is implementation-specific for each task type.
     *
     * @return The file format string representation of the task.
     */
    public abstract String toFileFormat();

    /**
     * Constructs a Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the completion status of the task.
     * Returns "X" if the task is done, otherwise returns a space " ".
     *
     * @return The status icon as a String.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the string representation of the task for display to the user.
     * The format is: [status icon] description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
