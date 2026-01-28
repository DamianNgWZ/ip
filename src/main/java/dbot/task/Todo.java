package dbot.task;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public static Todo parse(String input) throws DbotException {
        String description = input.substring(5).trim();
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

        Todo todo = new Todo(parts[2]);
        if (parts[1].equals("DONE")) {
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
