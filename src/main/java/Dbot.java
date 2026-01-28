import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Dbot {
    private final List<Task> tasks;
    private static final String LINE = "____________________________________________________________";

    public Dbot() {
        this.tasks = new ArrayList<>();
    }

    public void run() {
        printGreeting();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine().trim();
            String inputLowerCase = input.toLowerCase();
            System.out.println(LINE);

            try {
                if (inputLowerCase.equals("bye")) { // Terminating condition
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    break;
                } else if (inputLowerCase.equals("list")) { // Print list
                    showList();
                } else if (inputLowerCase.startsWith("mark ") || inputLowerCase.startsWith("unmark ")) {
                    updateTask(input);
                } else if (inputLowerCase.startsWith("delete ")) {
                    deleteTask(input);
                } else if (inputLowerCase.startsWith("todo ")) {
                    addTask(input, TaskType.TODO);
                } else if (inputLowerCase.startsWith("deadline ")) {
                    addTask(input, TaskType.DEADLINE);
                } else if (inputLowerCase.startsWith("event ")) {
                    addTask(input, TaskType.EVENT);
                } else { // Unknown command
                    throw new DbotException("Unknown command! Please try valid command");
                }
            } catch (DbotException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(LINE);
        }
        sc.close();
    }

    private void printGreeting() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Dbot");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    private void showList() {
        System.out.println("Here are the tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("No entries currently. Please add an entry");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
            }
            System.out.print(sb);
        }
    }

    private void updateTask(String input) throws DbotException {
        try {
            String lowerInput = input.toLowerCase();
            boolean isMark = lowerInput.startsWith("mark ");
            int index = Integer.parseInt(lowerInput.substring(isMark ? 5 : 7).trim()) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new DbotException("OOPS!!! Task number does not exist.");
            }

            Task task = tasks.get(index);
            if (isMark) {
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:");
            } else {
                task.markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println("  " + task);
        } catch (NumberFormatException e) {
            throw new DbotException("OOPS!!! Please provide a valid task number!");
        }
    }

    private void deleteTask(String input) throws DbotException {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new DbotException("OOPS!!! Task number does not exist.");
            }

            Task removedTask = tasks.remove(index);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new DbotException("OOPS!!! Please provide a valid task number!");
        }
    }

    private void addTask(String input, TaskType type) throws DbotException {
        Task task = switch (type) {
            case TODO -> Todo.parse(input);
            case DEADLINE -> Deadline.parse(input);
            case EVENT -> Event.parse(input);
        };

        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void main(String[] args) {
        new Dbot().run();
    }
}
