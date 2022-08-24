import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Duke {

    private static TaskList taskList = new TaskList();
    private static final Scanner sc = new Scanner(System.in);

    public static void printMessage(String input) throws DukeException {
        if(input.equalsIgnoreCase("bye")) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else if(input.equalsIgnoreCase("list")) {
            taskList.list();
        } else if(input.matches("^todo.*")) {
            try {
                ToDo todo = new ToDo(input.substring(5), false);
                taskList.add(todo);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            }
        } else if(input.matches("^deadline.*")) {
            try {
                String[] str = input.substring(9).split(" /by ");
                Deadline deadline = new Deadline(str[0], str[1], false);
                taskList.add(deadline);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("OOPS!!! The description and/or the time of a deadline cannot be empty.");
            }
        } else if(input.matches("^event.*")) {
            try {
                String[] str = input.substring(6).split(" /at ");
                Event event = new Event(str[0], str[1], false);
                taskList.add(event);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("OOPS!!! The description and/or the time of an event cannot be empty.");
            }
        } else if(input.matches("^mark [0-9]*$")) {
            try {
                int index = Integer.parseInt(input.substring(5)) - 1;
                taskList.markTaskAsDone(index);

            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("OOPS!!! You cannot mark a non-existent task as done.");
            }
        } else if(input.matches("^unmark [0-9]*$")) {
            try {
                int index = Integer.parseInt(input.substring(7)) - 1;
                taskList.markTaskAsUndone(index);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("OOPS!!! You cannot mark a non-existent task as undone.");
            }
        } else if(input.matches("^delete [0-9]*$")) {
            try {
                int index = Integer.parseInt(input.substring(7)) - 1;
                taskList.delete(index);
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("OOPS!!! You cannot delete a non-existent task.");
            }
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :(");
        }
    }

    public static void main(String[] args) {
        String welcomeMsg = "Hi there! Baymax at your service.";
        System.out.println(welcomeMsg);

        File dir = new File("data");
        File file = new File("data/TaskList.txt");

        //Creates the data directory if it does not exist, does nothing otherwise.
        try {
            if(!dir.exists()) {
                dir.mkdir();
            }
        } catch (SecurityException e) {
            System.out.println(e);
        }

        //Creates the file TaskList.txt in the data directory if it does not exist.
        try {
            file.createNewFile();
            Scanner scFile = new Scanner(file);
            while(scFile.hasNextLine()) {
                String taskString = scFile.nextLine();
                if (taskString.isBlank()) {
                    continue;
                }
                String[] taskElements = taskString.split(" \\| ");
                boolean isTaskDone = taskElements[1].equals("1");

                switch (taskElements[0]) {
                case "T":
                    taskList.add(new ToDo(taskElements[2], isTaskDone));
                    break;
                case "D":
                    taskList.add(new Deadline(taskElements[2], taskElements[3], isTaskDone));
                    break;
                case "E":
                    taskList.add(new Event(taskElements[2], taskElements[3], isTaskDone));
                    break;
                default:
                    break;
                }
            }
            scFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        String input = sc.nextLine();

        while(true) {
            try {
                printMessage(input);
            } catch (DukeException e) {
                System.out.println(e);
            } finally {
                input = sc.nextLine();
            }
        }
    }
}
