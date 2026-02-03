package dbot.parser;

import dbot.exception.DbotException;
import dbot.task.Deadline;
import dbot.task.Event;
import dbot.task.Task;
import dbot.task.Todo;

/**
 * Parses user input into commands and extracts relevant information.
 * The Parser handles command recognition, parameter extraction, and task creation.
 */
public class Parser {
    private static final int FIND_COMMAND_LENGTH = 5; // Length of "find "

    /**
     * Parses the user input string to determine the command type.
     * Recognizes commands such as bye, list, help, mark, unmark, delete, todo, deadline, and event.
     *
     * @param input The full user input string.
     * @return The CommandType corresponding to the input, or UNKNOWN if not recognized.
     */
    public static CommandType parseCommand(String input) {
        String lowerInput = input.toLowerCase().trim();

        if (lowerInput.equals("bye")) {
            return CommandType.BYE;
        }
        if (lowerInput.equals("list")) {
            return CommandType.LIST;
        }
        if (lowerInput.equals("help")) {
            return CommandType.HELP;
        }
        if (lowerInput.startsWith("mark ")) {
            return CommandType.MARK;
        }
        if (lowerInput.startsWith("unmark ")) {
            return CommandType.UNMARK;
        }
        if (lowerInput.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        if (lowerInput.startsWith("todo ")) {
            return CommandType.TODO;
        }
        if (lowerInput.startsWith("deadline ")) {
            return CommandType.DEADLINE;
        }
        if (lowerInput.startsWith("event ")) {
            return CommandType.EVENT;
        }
        if (lowerInput.startsWith("find")) {
            return CommandType.FIND;
        }

        return CommandType.UNKNOWN;
    }

    /**
     * Parses the input string to create a Task object of the appropriate type.
     * Delegates to the respective task class's parse method (Todo, Deadline, or Event).
     *
     * @param input The full user input string containing the task details.
     * @param type The type of task to create (TODO, DEADLINE, or EVENT).
     * @return A Task object of the specified type.
     * @throws DbotException If the task type is invalid or the input format is incorrect.
     */
    public static Task parseTask(String input, CommandType type) throws DbotException {
        return switch (type) {
        case TODO -> Todo.parse(input);
        case DEADLINE -> Deadline.parse(input);
        case EVENT -> Event.parse(input);
        default -> throw new DbotException("Cannot parse task for command type: " + type);
        };
    }

    /**
     * Parses the task number from a user command string.
     * Extracts the number after the command prefix and converts it to a zero-based index.
     *
     * @param input The full user input string (e.g., "mark 3" or "delete 1").
     * @param commandPrefix The command word to remove from the input (e.g., "mark " or "delete ").
     * @return The zero-based index of the task.
     * @throws DbotException If the task number is invalid, missing, or not a number.
     */
    public static int parseTaskNumber(String input, String commandPrefix) throws DbotException {
        try {
            return Integer.parseInt(input.substring(commandPrefix.length()).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new DbotException("OOPS!!! Please provide a valid task number!");
        }
    }

    /**
     * Parses the keyword from a find command string.
     * Extracts the search keyword after the "find " command prefix.
     *
     * @param input The full user input string (e.g., "find book").
     * @return The keyword to search for.
     */
    public static String parseKeyword(String input) {
        return input.substring(FIND_COMMAND_LENGTH).trim();
    }
}
