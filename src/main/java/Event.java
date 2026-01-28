import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Event parse(String input) throws DbotException {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) { // ensure from and to exists in input
            throw new DbotException("Please specify event with /from and /to");
        }

        String description = input.substring(6, fromIndex).trim();
        String fromString = input.substring(fromIndex + 6, toIndex).trim();
        String toString = input.substring(toIndex + 4).trim();

        if (description.isEmpty() || fromString.isEmpty() || toString.isEmpty()) { // ensure from to not empty
            throw new DbotException("Description, start time and end time cannot be empty.");
        }

        try {
            LocalDate from = LocalDate.parse(fromString, INPUT_FORMAT);
            LocalDate to = LocalDate.parse(toString, INPUT_FORMAT);
            return new Event(description, from, to);
        } catch (DateTimeParseException e) {
            throw new DbotException("Invalid date format! Please use dd-MM-yyyy (e.g., 02-12-2019)");
        }
    }

    public static Event fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        LocalDate from = LocalDate.parse(parts[3], INPUT_FORMAT);
        LocalDate to = LocalDate.parse(parts[4], INPUT_FORMAT);
        Event event = new Event(parts[2], from, to);
        if (parts[1].equals("DONE")) {
            event.markAsDone();
        }
        return event;
    }

    @Override
    public String toFileFormat() {
        return "E | " + (this.isDone ? "DONE" : "NOT DONE") + " | " + this.description + " | "
                + this.from.format(INPUT_FORMAT) + " | " + this.to.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
