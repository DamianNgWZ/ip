import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Dbot {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        printGreeting();

        Scanner sc = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

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
                    showList(tasks);
                } else if (inputLowerCase.startsWith("mark ") || inputLowerCase.startsWith("unmark ")) {
                    updateTask(tasks, input);
                } else if (inputLowerCase.startsWith("delete ")) {
                    deleteTask(tasks, input);
                } else if (inputLowerCase.startsWith("todo ")) {
                    addTask(tasks, input, TaskType.TODO);
                } else if (inputLowerCase.startsWith("deadline ")) {
                    addTask(tasks, input, TaskType.DEADLINE);
                } else if (inputLowerCase.startsWith("event ")) {
                    addTask(tasks, input, TaskType.EVENT);
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

    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Dbot");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    private static void showList(List<Task> tasks) {
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

    private static void updateTask(List<Task> tasks, String input) throws DbotException {
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

    private static void deleteTask(List<Task> tasks, String input) throws DbotException {
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

    private static void addTask(List<Task> tasks, String input, TaskType type) throws DbotException {
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
}
