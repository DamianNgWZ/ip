import java.util.Scanner;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm Dbot");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file. Starting with empty task list.");
    }

    public void showTaskAdded(String task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskDeleted(String task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskMarked(String task, boolean isDone) {
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
    }

    public void showTaskList(String list) {
        System.out.println("Here are the tasks in your list:");
        if (list.isEmpty()) {
            System.out.println("No entries currently. Please add an entry");
        } else {
            System.out.print(list);
        }
    }

    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  todo <description> - Add a todo task");
        System.out.println("  deadline <description> /by <dd-MM-yyyy> - Add a deadline task");
        System.out.println("  event <description> /from <dd-MM-yyyy> /to <dd-MM-yyyy> - Add an event task");
        System.out.println("  list - Show all tasks");
        System.out.println("  mark <task number> - Mark a task as done");
        System.out.println("  unmark <task number> - Mark a task as not done");
        System.out.println("  delete <task number> - Delete a task");
        System.out.println("  help - Show this help message");
        System.out.println("  bye - Exit the program");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void close() {
        scanner.close();
    }
}
