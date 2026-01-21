import java.util.Scanner;

public class Dbot {
    public static void main(String[] args) {
        String botName = "Dbot";
        String line = "____________________________________________________________";

        System.out.println(line);
        System.out.println("Hello! I'm " + botName);
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();
            System.out.println(line);
            System.out.println(input);
            System.out.println(line);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
        }
        sc.close();
    }
}
