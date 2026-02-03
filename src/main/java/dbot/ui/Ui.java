package dbot.ui;

import java.util.List;
import java.util.Scanner;

import dbot.task.Task;

/**
 * Handles interactions with the user.
 * The Ui class manages all input and output operations, including displaying messages
 * and reading user commands.
 */
public class Ui {
    /** The horizontal line separator used for formatting output. */
    private static final String LINE = "____________________________________________________________";

    /** The scanner used to read user input. */
    private final Scanner scanner;

    /**
     * Constructs a Ui object.
     * Initializes the scanner for reading user input from the console.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm Dbot");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Reads a command from the user.
     * Trims whitespace from the input.
     *
     * @return The user's input command as a trimmed string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message indicating that tasks could not be loaded from the file.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Starting with empty task list.");
    }

    /**
     * Displays a confirmation message when a task is added.
     *
     * @param task The string representation of the added task.
     * @param taskCount The total number of tasks after adding.
     */
    public void showTaskAdded(String task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays a confirmation message when a task is deleted.
     *
     * @param task The string representation of the deleted task.
     * @param taskCount The total number of tasks after deletion.
     */
    public void showTaskDeleted(String task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays a confirmation message when a task's completion status is updated.
     *
     * @param task The string representation of the task.
     * @param isDone Whether the task was marked as done (true) or not done (false).
     */
    public void showTaskMarked(String task, boolean isDone) {
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
    }

    /**
     * Displays the list of all tasks.
     *
     * @param list The formatted string containing all tasks.
     */
    public void showTaskList(String list) {
        if (list.isEmpty()) {
            System.out.println("No tasks in your list yet!");
        } else {
            System.out.println("Here are the tasks in your list:");
            System.out.print(list);
        }
    }

    /**
     * Displays the help message showing all available commands.
     */
    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  todo <description> - Add a todo task");
        System.out.println("  deadline <description> /by <dd-MM-yyyy> - Add a deadline task");
        System.out.println("  event <description> /from <dd-MM-yyyy> /to <dd-MM-yyyy> - Add an event task");
        System.out.println("  list - Show all tasks");
        System.out.println("  mark <task number> - Mark a task as done");
        System.out.println("  unmark <task number> - Mark a task as not done");
        System.out.println("  delete <task number> - Delete a task");
        System.out.println("  find <keyword> - Find tasks containing keyword");
        System.out.println("  help - Show this help message");
        System.out.println("  bye - Exit the program");
    }

    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param matchingTasks The list of tasks that match the search.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found!");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + "." + matchingTasks.get(i));
            }
        }
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Closes the scanner and releases resources.
     */
    public void close() {
        scanner.close();
    }
}
