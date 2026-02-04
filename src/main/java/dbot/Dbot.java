package dbot;

import java.io.IOException;
import java.util.List;

import dbot.exception.DbotException;
import dbot.parser.CommandType;
import dbot.parser.Parser;
import dbot.storage.Storage;
import dbot.task.Task;
import dbot.tasklist.TaskList;
import dbot.ui.Ui;

/**
 * The main class for the Dbot chatbot application.
 * Dbot is a task management chatbot that helps users track their todos, deadlines, and events.
 * It handles user input, executes commands, and manages task persistence.
 */
public class Dbot {
    private TaskList tasks;
    private final Storage storage;
    private final Ui ui;

    /**
     * Constructs a Dbot instance with the specified file path for data storage.
     * Loads existing tasks from the file if available, otherwise starts with an empty task list.
     *
     * @param filePath The file path where tasks are saved and loaded from.
     */
    public Dbot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            // Only for REAL errors. File doesnt exist returns empty list
            ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the chatbot.
     * Continuously reads user input, parses commands, executes them,
     * and displays results until the user exits with the "bye" command.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            try {
                CommandType command = Parser.parseCommand(input);

                switch (command) {
                case BYE: // Terminating condition
                    ui.showGoodbye();
                    ui.showLine();
                    ui.close();
                    return; // Exit run() method
                case LIST: // Print list
                    showList();
                    break;
                case HELP: // Show help
                    ui.showHelp();
                    break;
                case FIND: // Find tasks
                    findTasks(input);
                    break;
                case MARK:
                case UNMARK:
                    updateTask(input, command);
                    break;
                case DELETE:
                    deleteTask(input);
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    addTask(input, command);
                    break;
                default: // Unknown command
                    throw new DbotException("Unknown command! Type 'help' to see available commands.");
                }
            } catch (DbotException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    /**
     * Displays the current list of all tasks to the user.
     */
    private void showList() {
        ui.showTaskList(tasks.getFormattedList());
    }

    /**
     * Updates a task's completion status by marking it as done or undone.
     *
     * @param input The user input string containing the command and task number.
     * @param command The type of command (MARK or UNMARK).
     * @throws DbotException If the task number is invalid or out of range.
     */
    private void updateTask(String input, CommandType command) throws DbotException {
        boolean isMark = (command == CommandType.MARK);
        int index = Parser.parseTaskNumber(input, isMark ? "mark " : "unmark ");

        Task task = tasks.get(index);
        if (isMark) {
            task.markAsDone();
        } else {
            task.markAsUndone();
        }
        ui.showTaskMarked(task.toString(), isMark);
        saveTasks(); // Save after marking/unmarking
    }

    /**
     * Deletes a task from the task list.
     *
     * @param input The user input string containing the delete command and task number.
     * @throws DbotException If the task number is invalid or out of range.
     */
    private void deleteTask(String input) throws DbotException {
        int index = Parser.parseTaskNumber(input, "delete ");
        Task removedTask = tasks.delete(index);
        ui.showTaskDeleted(removedTask.toString(), tasks.size());
        saveTasks(); // Save after deleting
    }

    /**
     * Finds and displays tasks that match the given keyword.
     *
     * @param input The user input string containing the find command and keyword.
     */
    private void findTasks(String input) {
        String keyword = Parser.parseKeyword(input);
        List<Task> matchingTasks = tasks.find(keyword);
        ui.showMatchingTasks(matchingTasks);
    }

    /**
     * Adds a new task to the task list.
     * The task type (Todo, Deadline, or Event) is determined by the command type.
     *
     * @param input The user input string containing the task details.
     * @param type The type of task to add (TODO, DEADLINE, or EVENT).
     * @throws DbotException If the input format is invalid for the task type.
     */
    private void addTask(String input, CommandType type) throws DbotException {
        Task task = Parser.parseTask(input, type);
        tasks.add(task);
        ui.showTaskAdded(task.toString(), tasks.size());
        saveTasks(); // Save after adding
    }

    /**
     * Saves the current task list to the storage file.
     * Displays an error message if saving fails.
     */
    private void saveTasks() {
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showError("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Generates a response for the user's chat message.
     * This method is used by the GUI to get Dbot's response without using the CLI.
     *
     * @param input The user's input string.
     * @return Dbot's response as a String.
     */
    public String getResponse(String input) {
        try {
            CommandType command = Parser.parseCommand(input);
            switch (command) {
            case BYE:
                return ui.getGoodbyeMessage();
            case LIST:
                return ui.getTaskListMessage(tasks.getFormattedList());
            case HELP:
                return ui.getHelpMessage();
            case FIND:
                String keyword = Parser.parseKeyword(input);
                List<Task> matchingTasks = tasks.find(keyword);
                return ui.getMatchingTasksMessage(matchingTasks);
            case MARK:
            case UNMARK:
                boolean isMark = (command == CommandType.MARK);
                int markIndex = Parser.parseTaskNumber(input, isMark ? "mark " : "unmark ");
                Task markTask = tasks.get(markIndex);
                if (isMark) {
                    markTask.markAsDone();
                } else {
                    markTask.markAsUndone();
                }
                saveTasks();
                return ui.getTaskMarkedMessage(markTask.toString(), isMark);
            case DELETE:
                int deleteIndex = Parser.parseTaskNumber(input, "delete ");
                Task removedTask = tasks.delete(deleteIndex);
                saveTasks();
                return ui.getTaskDeletedMessage(removedTask.toString(), tasks.size());
            case TODO:
            case DEADLINE:
            case EVENT:
                Task newTask = Parser.parseTask(input, command);
                tasks.add(newTask);
                saveTasks();
                return ui.getTaskAddedMessage(newTask.toString(), tasks.size());
            default:
                return "Unknown command! Type 'help' to see available commands.";
            }
        } catch (DbotException e) {
            return e.getMessage();
        }
    }

    /**
     * The entry point of the Dbot application.
     * Creates a new Dbot instance and starts the chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Dbot("./data/dbot.txt").run();
    }
}
