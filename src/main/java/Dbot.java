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

            if (inputLowerCase.equals("bye")) { // Terminating condition
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (inputLowerCase.equals("list")) { // Print list
                showList(tasks);
            } else if (inputLowerCase.startsWith("mark ") || input.toLowerCase().startsWith("unmark ")) {
                updateTask(tasks, input);
            } else { // Add to list
                tasks.add(new Task(input));
                System.out.println("added: " + input);
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
                sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
            System.out.print(sb);
        }
    }

    private static void updateTask(List<Task> tasks, String input) {
        try {
            String lowerInput = input.toLowerCase();
            boolean isMark = lowerInput.startsWith("mark ");
            int index = Integer.parseInt(lowerInput.substring(isMark ? 5 : 7).trim()) - 1;

            if (index >= 0 && index < tasks.size()) {
                Task task = tasks.get(index);
                if (isMark) {
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                } else {
                    task.markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                }
                System.out.println("  " + task);
            } else {
                System.out.println("Task number does not exist");
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
