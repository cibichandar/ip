import java.util.Scanner;

/** A simple command-line task manager. */
public class Fein {
    /** Starts Fein and processes commands until the user enters {@code bye}. */
    public static void main(String[] args) {
        String separator = "_".repeat(100);
        String banner = "oooooooooooo           o8o                     \n"
                + "`888'     `8           `\"'                     \n"
                + " 888          .ooooo.  oooo  ooo. .oo.         \n"
                + " 888oooo8    d88' `88b `888  `888P\"Y88b        \n"
                + " 888    \"    888ooo888  888   888   888        \n"
                + " 888         888    .o  888   888   888        \n"
                + "o888o        `Y8bod8P' o888o o888o o888o       \n";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Fein.");
        System.out.println("What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            String command = scanner.nextLine();

            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            if (command.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i].toDisplayString());
                }
            } else if (command.startsWith("mark ")) {
                markTask(command, tasks, taskCount);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command, tasks, taskCount);
            } else {
                tasks[taskCount] = createTask(command);
                taskCount++;
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1].toDisplayString());
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
            }

            System.out.println(separator);
        }
    }

    /** Creates the appropriate task subtype from a user command. */
    private static Task createTask(String command) {
        if (command.startsWith("deadline ")) {
            String remainder = command.substring("deadline ".length());
            int separator = remainder.indexOf(" /by ");
            if (separator >= 0) {
                return new Deadline(remainder.substring(0, separator),
                        remainder.substring(separator + " /by ".length()));
            }
        } else if (command.startsWith("event ")) {
            String remainder = command.substring("event ".length());
            int fromSeparator = remainder.indexOf(" /from ");
            int toSeparator = remainder.indexOf(" /to ");
            if (fromSeparator >= 0 && toSeparator > fromSeparator) {
                return new Event(remainder.substring(0, fromSeparator),
                        remainder.substring(fromSeparator + " /from ".length(), toSeparator),
                        remainder.substring(toSeparator + " /to ".length()));
            }
        }
        return new Todo(command.startsWith("todo ") ? command.substring("todo ".length()) : command);
    }

    /** Marks the numbered task as complete and prints a confirmation. */
    private static void markTask(String command, Task[] tasks, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring("mark ".length()).trim());
            if (taskNumber < 1 || taskNumber > taskCount) {
                System.out.println(" That task does not exist.");
                return;
            }

            int taskIndex = taskNumber - 1;
            tasks[taskIndex].markAsDone();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks[taskIndex].toDisplayString());
        } catch (NumberFormatException exception) {
            System.out.println(" Please provide a valid task number.");
        }
    }

    /** Marks the numbered task as not complete and prints a confirmation. */
    private static void unmarkTask(String command, Task[] tasks, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring("unmark ".length()).trim());
            if (taskNumber < 1 || taskNumber > taskCount) {
                System.out.println(" That task does not exist.");
                return;
            }

            int taskIndex = taskNumber - 1;
            tasks[taskIndex].markAsNotDone();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks[taskIndex].toDisplayString());
        } catch (NumberFormatException exception) {
            System.out.println(" Please provide a valid task number.");
        }
    }
}
