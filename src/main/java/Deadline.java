public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
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

    public static Deadline fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        Deadline deadline = new Deadline(parts[2], parts[3]);
        if (parts[1].equals("DONE")) {
            deadline.markAsDone();
        }
        return deadline;
    }

    @Override
    public String toFileFormat() {
        return "D | " + (this.isDone ? "DONE" : "NOT DONE") + " | " + this.description + " | " + this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }

}
