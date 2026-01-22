public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public static Todo parse(String input) throws DbotException{
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new DbotException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }
}
