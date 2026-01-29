package dbot.parser;

import dbot.exception.DbotException;
import dbot.task.Deadline;
import dbot.task.Event;
import dbot.task.Task;
import dbot.task.Todo;

public class Parser {
    private static final int FIND_COMMAND_LENGTH = 5; // Length of "find "

    public static CommandType parseCommand(String input) {
        String lowerInput = input.toLowerCase().trim();

        if (lowerInput.equals("bye")) return CommandType.BYE;
        if (lowerInput.equals("list")) return CommandType.LIST;
        if (lowerInput.equals("help")) return CommandType.HELP;
        if (lowerInput.startsWith("mark ")) return CommandType.MARK;
        if (lowerInput.startsWith("unmark ")) return CommandType.UNMARK;
        if (lowerInput.startsWith("delete ")) return CommandType.DELETE;
        if (lowerInput.startsWith("todo ")) return CommandType.TODO;
        if (lowerInput.startsWith("deadline ")) return CommandType.DEADLINE;
        if (lowerInput.startsWith("event ")) return CommandType.EVENT;
        if (lowerInput.startsWith("find")) {
            return CommandType.FIND;
        }

        return CommandType.UNKNOWN;
    }

    public static Task parseTask(String input, CommandType type) throws DbotException {
        return switch (type) {
            case TODO -> Todo.parse(input);
            case DEADLINE -> Deadline.parse(input);
            case EVENT -> Event.parse(input);
            default -> throw new DbotException("Cannot parse task for command type: " + type);
        };
    }

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
