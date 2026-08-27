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

            try {
                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(separator);
                    break;
                }

                if (command.equals("list")) {
                    if (taskCount == 0) {
                        System.out.println(" Nothing on the list yet, Fein's waiting on you");
                    } else {
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < taskCount; i++) {
                            System.out.println(" " + (i + 1) + "." + tasks[i]);
                        }
                    }
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    markTask(command, tasks, taskCount);
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    unmarkTask(command, tasks, taskCount);
                } else {
                    tasks[taskCount] = createTask(command);
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                }
            } catch (FeinException exception) {
                System.out.println(" " + exception.getMessage());
            }

            System.out.println(separator);
        }
    }

    /** Creates the appropriate task subtype from a user command. */
    private static Task createTask(String command) throws FeinException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.length() > "todo".length()
                    ? command.substring("todo".length()).trim() : "";
            if (description.isEmpty()) {
                throw new FeinException("OOPS!!! Can't fein for nothing, give the todo a description");
            }
            return new Todo(description);
        }

        if (command.equals("deadline") || command.startsWith("deadline ")) {
            String remainder = command.length() > "deadline".length()
                    ? command.substring("deadline".length()).trim() : "";
            if (remainder.isEmpty()) {
                throw new FeinException("OOPS!!! Empty deadline? Fein needs a description too");
            }
            int separator = remainder.indexOf(" /by");
            if (separator < 0) {
                throw new FeinException("OOPS!!! When's it due? Add a /by");
            }
            String description = remainder.substring(0, separator).trim();
            String by = remainder.substring(separator + " /by".length()).trim();
            if (description.isEmpty()) {
                throw new FeinException("OOPS!!! Empty deadline? Fein needs a description too");
            }
            if (by.isEmpty()) {
                throw new FeinException("OOPS!!! You left the date blank after /by");
            }
            return new Deadline(description, by);
        }

        if (command.equals("event") || command.startsWith("event ")) {
            String remainder = command.length() > "event".length()
                    ? command.substring("event".length()).trim() : "";
            if (remainder.isEmpty()) {
                throw new FeinException("OOPS!!! Empty event? Fein needs a description too");
            }
            int fromSeparator = remainder.indexOf(" /from");
            int toSeparator = remainder.indexOf(" /to");
            if (fromSeparator < 0 || toSeparator < 0) {
                if (fromSeparator >= 0 && toSeparator < 0) {
                    throw new FeinException("OOPS!!! Missing the /to, when does it end?");
                }
                throw new FeinException("OOPS!!! Fein needs a /from and /to for this one");
            }
            String description = remainder.substring(0, fromSeparator).trim();
            String from = remainder.substring(fromSeparator + " /from".length(), toSeparator).trim();
            String to = remainder.substring(toSeparator + " /to".length()).trim();
            if (description.isEmpty()) {
                throw new FeinException("OOPS!!! Empty event? Fein needs a description too");
            }
            if (from.isEmpty()) {
                throw new FeinException("OOPS!!! You left /from blank");
            }
            if (to.isEmpty()) {
                throw new FeinException("OOPS!!! Missing the /to, when does it end?");
            }
            return new Event(description, from, to);
        }

        throw new FeinException("OOPS!!! Fein don't know that one, try again");
    }

    /** Marks the numbered task as complete and prints a confirmation. */
    private static void markTask(String command, Task[] tasks, int taskCount) throws FeinException {
        int taskNumber = parseTaskNumber(command, "mark");
        if (taskNumber <= 0) {
            throw new FeinException("OOPS!!! Task numbers start from 1, not 0");
        }
        if (taskNumber > taskCount) {
            throw new FeinException("OOPS!!! Task " + taskNumber + " don't exist, check your list again");
        }

        int taskIndex = taskNumber - 1;
        tasks[taskIndex].markAsDone();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + tasks[taskIndex]);
    }

    /** Marks the numbered task as not complete and prints a confirmation. */
    private static void unmarkTask(String command, Task[] tasks, int taskCount) throws FeinException {
        int taskNumber = parseTaskNumber(command, "unmark");
        if (taskNumber <= 0) {
            throw new FeinException("OOPS!!! Task numbers start from 1, not 0");
        }
        if (taskNumber > taskCount) {
            throw new FeinException("OOPS!!! Task " + taskNumber + " don't exist, check your list again");
        }

        int taskIndex = taskNumber - 1;
        tasks[taskIndex].markAsNotDone();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks[taskIndex]);
    }

    /** Parses a mark or unmark argument and reports malformed input consistently. */
    private static int parseTaskNumber(String command, String action) throws FeinException {
        String value = command.length() > action.length()
                ? command.substring(action.length()).trim() : "";
        if (value.isEmpty()) {
            throw new FeinException("OOPS!!! Mark what? Give Fein a task number");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new FeinException("OOPS!!! That's not a number, Fein can't read minds");
        }
    }
}
