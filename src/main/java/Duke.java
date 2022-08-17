import java.util.Scanner;
import java.util.regex.Pattern;

public class Duke {

    private static TaskList taskList = new TaskList();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String welcomeMsg = "Hi there! Baymax at your service.";
        System.out.println(welcomeMsg);
        String input = sc.nextLine();
        while(true) {
            if(input.equalsIgnoreCase("bye")) {
                break;
            }

            if(input.equalsIgnoreCase("list")) {
                taskList.list();
                input = sc.nextLine();
                continue;
            }

            if(input.matches("^mark [0-9]*$")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                taskList.markTaskAsDone(index);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + taskList.getTask(index));
                input = sc.nextLine();
                continue;
            }

            if(input.matches("^unmark [0-9]$")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                taskList.markTaskAsUndone(index);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + taskList.getTask(index));
                input = sc.nextLine();
                continue;
            }
            taskList.add(new Task(input));
            input = sc.nextLine();
        }
        System.out.println("Goodbye!");
    }
}
