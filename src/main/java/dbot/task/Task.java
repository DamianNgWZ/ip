package dbot.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public abstract String toFileFormat();

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return this.isDone ? "X" : " "; // mark task done with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public boolean isDone() { return this.isDone; }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
