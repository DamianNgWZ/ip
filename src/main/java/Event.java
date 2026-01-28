public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Event parse(String input) throws DbotException{
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) { // ensure from and to exists in input
            throw new DbotException("Please specify event with /from and /to");
        }

        String description = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) { // ensure from to not empty
            throw new DbotException("Description, start time and end time cannot be empty.");
        }
        return new Event(description, from, to);
    }

    public static Event fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        Event event = new Event(parts[2], parts[3], parts[4]);
        if (parts[1].equals("DONE")) {
            event.markAsDone();
        }
        return event;
    }

    @Override
    public String toFileFormat() {
        return "E | " + (this.isDone ? "DONE" : "NOT DONE") + " | " + this.description + " | "
                + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
