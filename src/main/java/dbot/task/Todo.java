package dbot.task;

import dbot.exception.DbotException;

public class Todo extends Task {
    private static final int COMMAND_LENGTH = 5; // Length of "todo "
    private static final int FILE_DESCRIPTION_INDEX = 2;
    private static final int FILE_STATUS_INDEX = 1;
    private static final String DONE_STATUS = "DONE";

    public Todo(String description) {
        super(description);
    }

    public static Todo parse(String input) throws DbotException {
        String description = input.substring(COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new DbotException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    public static Todo fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        Todo todo = new Todo(parts[FILE_DESCRIPTION_INDEX]);
        if (parts[FILE_STATUS_INDEX].equals(DONE_STATUS)) {
            todo.markAsDone();
        }
        return todo;
    }

    @Override
    public String toFileFormat() {
        return "T | " + (this.isDone ? "DONE" : "NOT DONE") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
