public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public static Todo parse(String input) throws DbotException{
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new DbotException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
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
