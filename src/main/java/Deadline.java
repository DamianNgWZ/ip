public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    public static Deadline parse(String input) throws DbotException{
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            throw new DbotException("Please specify deadline with /by");
        }
        String description = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new DbotException("Description and deadline cannot be empty.");
        }
        return new Deadline(description, by);
    }
}
