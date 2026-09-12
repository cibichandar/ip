package fein;

import java.util.List;

import fein.task.Task;
import fein.task.TaskList;

/** Coordinates Fein's user interface, parser, task list, and storage. */
public class Fein {
    /** Handles command-line input and output. */
    private final Ui ui;

    /** Converts user commands into tasks and arguments. */
    private final Parser parser;

    /** Owns the current tasks. */
    private TaskList tasks;

    /** Loads and saves tasks. */
    private final Storage storage;

    /** Identifies the most recently processed command for GUI styling. */
    private String commandType;

    /** Creates Fein using the default task file. */
    public Fein() {
        this("data/fein.txt");
    }

    /** Creates Fein using the supplied task file. */
    public Fein(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);
        commandType = "";
        try {
            tasks = new TaskList(storage.load());
        } catch (FeinException exception) {
            ui.showError(exception.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Returns Fein's response to a command and applies any requested task-list changes.
     *
     * @param command the command entered by the user
     * @return the response that should be displayed in the chat
     */
    public String getResponse(String command) {
        String normalizedCommand = command.trim();
        commandType = "error";
        try {
            if (normalizedCommand.equals("bye")) {
                return getByeResponse();
            }
            if (normalizedCommand.equals("list")) {
                return getListResponse();
            }
            if (normalizedCommand.equals("find") || normalizedCommand.startsWith("find ")) {
                return getFindResponse(normalizedCommand);
            }
            if (normalizedCommand.equals("mark") || normalizedCommand.startsWith("mark ")) {
                return changeTaskStatus(normalizedCommand, true);
            }
            if (normalizedCommand.equals("unmark") || normalizedCommand.startsWith("unmark ")) {
                return changeTaskStatus(normalizedCommand, false);
            }
            if (normalizedCommand.equals("delete") || normalizedCommand.startsWith("delete ")) {
                return deleteTask(normalizedCommand);
            }

            return addTask(normalizedCommand);
        } catch (FeinException exception) {
            commandType = "error";
            return exception.getMessage();
        }
    }

    /** Returns Fein's farewell response. */
    private String getByeResponse() {
        commandType = "bye";
        return "Bye. Hope to see you again soon!";
    }

    /** Returns the current task-list response. */
    private String getListResponse() {
        commandType = "list";
        return formatTasks(tasks.asList(), "Here are the tasks in your list:",
                "Nothing on the list yet, Fein's waiting on you");
    }

    /** Returns the response for a find command. */
    private String getFindResponse(String command) throws FeinException {
        commandType = "find";
        String keyword = parser.parseFindKeyword(command);
        return formatTasks(tasks.find(keyword), "Here are the matching tasks in your list:",
                "No matching tasks found");
    }

    /** Changes a task's completion state and returns Fein's response. */
    private String changeTaskStatus(String command, boolean shouldMark) throws FeinException {
        commandType = shouldMark ? "mark" : "unmark";
        int taskNumber = parser.parseTaskNumber(command, shouldMark ? "mark" : "unmark");
        Task task = shouldMark ? tasks.mark(taskNumber) : tasks.unmark(taskNumber);
        storage.save(tasks);
        return shouldMark
                ? "Nice! I've marked this task as done:\n" + task
                : "OK, I've marked this task as not done yet:\n" + task;
    }

    /** Deletes a task and returns Fein's response. */
    private String deleteTask(String command) throws FeinException {
        commandType = "delete";
        Task task = tasks.delete(parser.parseTaskNumber(command, "delete"));
        storage.save(tasks);
        return "Noted. I've removed this task:\n" + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /** Adds a task and returns Fein's response. */
    private String addTask(String command) throws FeinException {
        Task task = parser.parseTask(command);
        tasks.add(task);
        storage.save(tasks);
        commandType = "add";
        return "Got it. I've added this task:\n" + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /** Returns the type of the most recently processed command. */
    public String getCommandType() {
        return commandType;
    }

    /** Returns a formatted response containing a heading and numbered tasks. */
    private String formatTasks(List<Task> matchingTasks, String heading, String emptyMessage) {
        if (matchingTasks.isEmpty()) {
            return emptyMessage;
        }

        StringBuilder response = new StringBuilder(heading);
        for (int i = 0; i < matchingTasks.size(); i++) {
            response.append("\n").append(i + 1).append(".").append(matchingTasks.get(i));
        }
        return response.toString();
    }

    /** Runs Fein until the user enters {@code bye} or closes input. */
    public void run() {
        ui.showWelcome();
        while (true) {
            String command = ui.readCommand();
            if (command == null) {
                break;
            }
            ui.showSeparator();
            try {
                if (command.equals("bye")) {
                    ui.showGoodbye();
                    break;
                }
                handleCommand(command);
            } catch (FeinException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showSeparator();
        }
    }

    /** Dispatches one command to the object responsible for that operation. */
    private void handleCommand(String command) throws FeinException {
        if (command.equals("list")) {
            ui.showTasks(tasks);
        } else if (command.equals("find") || command.startsWith("find ")) {
            String keyword = parser.parseFindKeyword(command);
            ui.showMatchingTasks(tasks.find(keyword));
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            Task task = tasks.mark(parser.parseTaskNumber(command, "mark"));
            storage.save(tasks);
            ui.showTaskMarked(task);
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            Task task = tasks.unmark(parser.parseTaskNumber(command, "unmark"));
            storage.save(tasks);
            ui.showTaskUnmarked(task);
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            Task deletedTask = tasks.delete(parser.parseTaskNumber(command, "delete"));
            storage.save(tasks);
            ui.showTaskDeleted(deletedTask, tasks.size());
        } else {
            Task task = parser.parseTask(command);
            tasks.add(task);
            storage.save(tasks);
            ui.showTaskAdded(task, tasks.size());
        }
    }

    /** Starts Fein with its default storage file. */
    public static void main(String[] args) {
        new Fein().run();
    }
}
