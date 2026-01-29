package dbot.task;

import dbot.exception.DbotException;

/**
 * Represents a todo task without any date/time attached to it.
 * A Todo only has a description and completion status.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Parses user input to create a Todo task.
     * Extracts the description from the input string after the "todo" command.
     *
     * @param input The full user input string (e.g., "todo read book").
     * @return A new Todo task with the parsed description.
     * @throws DbotException If the description is empty.
     */
    public static Todo parse(String input) throws DbotException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new DbotException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    /**
     * Creates a Todo task from a saved file format string.
     * Parses the file line and restores the todo's description and completion status.
     *
     * @param line The line from the file (format: "T | DONE/NOT DONE | description").
     * @return A Todo task restored from the file format.
     */
    public static Todo fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        Todo todo = new Todo(parts[2]);
        if (parts[1].equals("DONE")) {
            todo.markAsDone();
        }
        return todo;
    }

    /**
     * Returns the string representation of this todo for saving to a file.
     * Format: "T | DONE/NOT DONE | description"
     *
     * @return The file format string representation.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (this.isDone ? "DONE" : "NOT DONE") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
