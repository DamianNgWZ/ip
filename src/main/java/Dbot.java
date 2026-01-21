import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Dbot {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        printGreeting();

        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<>();

        while (true) {
            String input = sc.nextLine().trim();
            System.out.println(LINE);

            if (input.equalsIgnoreCase("bye")) { // Terminating condition
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (input.equalsIgnoreCase("list")) { // Print list
                showList(list);
            } else { // Add to list
                list.add(input);
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

    private static void showList(List<String> list) {
        if (list.isEmpty()) {
            System.out.println("No entries currently. Please add an entry");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(i + 1).append(". ").append(list.get(i)).append("\n");
            }
            System.out.print(sb);
        }
    }
}