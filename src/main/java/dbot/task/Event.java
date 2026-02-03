package dbot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import dbot.exception.DbotException;

/**
 * Represents an event task with a start and end date.
 * An Event task has a description, a start date, and an end date.
 */
public class Event extends Task {
    private static final int COMMAND_LENGTH = 6; // Length of "event "
    private static final int FROM_PREFIX_LENGTH = 6; // Length of "/from "
    private static final int TO_PREFIX_LENGTH = 4; // Length of "/to "
    private static final int FILE_DESCRIPTION_INDEX = 2;
    private static final int FILE_STATUS_INDEX = 1;
    private static final int FILE_FROM_DATE_INDEX = 3;
    private static final int FILE_TO_DATE_INDEX = 4;
    private static final String DONE_STATUS = "DONE";
    /** The date format used for parsing user input (dd-MM-yyyy). */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /** The date format used for displaying dates to the user (MMM dd yyyy). */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /** The start date of this event. */
    protected LocalDate from;

    /** The end date of this event. */
    protected LocalDate to;

    /**
     * Constructs an Event task with the given description, start date, and end date.
     *
     * @param description The description of the event.
     * @param from The start date of the event.
     * @param to The end date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Parses user input to create an Event task.
     * Extracts the description, start date, and end date from the input string.
     * Expected format: "event description /from dd-MM-yyyy /to dd-MM-yyyy"
     *
     * @param input The full user input string (e.g., "event project meeting /from 02-12-2019 /to 03-12-2019").
     * @return A new Event task with the parsed description and dates.
     * @throws DbotException If the /from or /to keywords are missing, date format is invalid,
     *                       or description/dates are empty.
     */
    public static Event parse(String input) throws DbotException {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) { // ensure from and to exists in input
            throw new DbotException("Please specify event with /from and /to");
        }

        String description = input.substring(COMMAND_LENGTH, fromIndex).trim();
        String fromString = input.substring(fromIndex + FROM_PREFIX_LENGTH, toIndex).trim();
        String toString = input.substring(toIndex + TO_PREFIX_LENGTH).trim();

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

    /**
     * Creates an Event task from a saved file format string.
     * Parses the file line and restores the event's description, completion status, and dates.
     *
     * @param line The line from the file (format: "E | DONE/NOT DONE | description | dd-MM-yyyy | dd-MM-yyyy").
     * @return An Event task restored from the file format.
     */
    public static Event fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        LocalDate from = LocalDate.parse(parts[FILE_FROM_DATE_INDEX], INPUT_FORMAT);
        LocalDate to = LocalDate.parse(parts[FILE_TO_DATE_INDEX], INPUT_FORMAT);
        Event event = new Event(parts[FILE_DESCRIPTION_INDEX], from, to);
        if (parts[FILE_STATUS_INDEX].equals(DONE_STATUS)) {
            event.markAsDone();
        }
        return event;
    }

    /**
     * Returns the string representation of this event for saving to a file.
     * Format: "E | DONE/NOT DONE | description | dd-MM-yyyy | dd-MM-yyyy"
     *
     * @return The file format string representation.
     */
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
