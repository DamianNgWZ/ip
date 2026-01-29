package dbot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import dbot.exception.DbotException;

public class Deadline extends Task {
    private static final int COMMAND_LENGTH = 9; // Length of "deadline "
    private static final int BY_PREFIX_LENGTH = 4; // Length of "/by "
    private static final int FILE_DESCRIPTION_INDEX = 2;
    private static final int FILE_STATUS_INDEX = 1;
    private static final int FILE_DATE_INDEX = 3;
    private static final String DONE_STATUS = "DONE";
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public static Deadline parse(String input) throws DbotException {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            throw new DbotException("Please specify deadline with /by");
        }
        String description = input.substring(COMMAND_LENGTH, byIndex).trim();
        String byString = input.substring(byIndex + BY_PREFIX_LENGTH).trim();

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

        LocalDate by = LocalDate.parse(parts[FILE_DATE_INDEX], INPUT_FORMAT);
        Deadline deadline = new Deadline(parts[FILE_DESCRIPTION_INDEX], by);
        if (parts[FILE_STATUS_INDEX].equals(DONE_STATUS)) {
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
