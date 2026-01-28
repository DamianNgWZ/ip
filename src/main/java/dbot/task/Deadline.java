package dbot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public static Deadline parse(String input) throws DbotException {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            throw new DbotException("Please specify deadline with /by");
        }
        String description = input.substring(9, byIndex).trim();
        String byString = input.substring(byIndex + 4).trim();

        if (description.isEmpty() || byString.isEmpty()) {
            throw new DbotException("Description and deadline cannot be empty.");
        }

        try {
            LocalDate by = LocalDate.parse(byString, INPUT_FORMAT);
            return new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new DbotException("Invalid date format! Please use dd-MM-yyyy (e.g., 02-12-2019)");
        }
    }

    public static Deadline fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        LocalDate by = LocalDate.parse(parts[3], INPUT_FORMAT);
        Deadline deadline = new Deadline(parts[2], by);
        if (parts[1].equals("DONE")) {
            deadline.markAsDone();
        }
        return deadline;
    }

    @Override
    public String toFileFormat() {
        return "D | " + (this.isDone ? "DONE" : "NOT DONE") + " | "
                + this.description + " | " + this.by.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(OUTPUT_FORMAT) + ")";
    }
}
