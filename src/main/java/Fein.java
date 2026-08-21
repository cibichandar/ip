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
        String[] tasks = new String[100];
        boolean[] completed = new boolean[100];
        int taskCount = 0;

        while (true) {
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
                    String status = completed[i] ? "X" : " ";
                    System.out.println(" " + (i + 1) + ".[" + status + "] " + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                markTask(command, tasks, completed, taskCount);
            } else if (command.startsWith("unmark ")) {
                unmarkTask(command, tasks, completed, taskCount);
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
            }

            System.out.println(separator);
        }
    }

    /** Marks the numbered task as complete and prints a confirmation. */
    private static void markTask(String command, String[] tasks, boolean[] completed, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring("mark ".length()).trim());
            if (taskNumber < 1 || taskNumber > taskCount) {
                System.out.println(" That task does not exist.");
                return;
            }

            int taskIndex = taskNumber - 1;
            completed[taskIndex] = true;
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + tasks[taskIndex]);
        } catch (NumberFormatException exception) {
            System.out.println(" Please provide a valid task number.");
        }
    }

    /** Marks the numbered task as not complete and prints a confirmation. */
    private static void unmarkTask(String command, String[] tasks, boolean[] completed, int taskCount) {
        try {
            int taskNumber = Integer.parseInt(command.substring("unmark ".length()).trim());
            if (taskNumber < 1 || taskNumber > taskCount) {
                System.out.println(" That task does not exist.");
                return;
            }

            int taskIndex = taskNumber - 1;
            completed[taskIndex] = false;
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + tasks[taskIndex]);
        } catch (NumberFormatException exception) {
            System.out.println(" Please provide a valid task number.");
        }
    }
}
