package fein;

import fein.task.Deadline;
import fein.task.Event;
import fein.task.Task;
import fein.task.Todo;

/** Converts user commands into tasks and command arguments. */
public class Parser {
    /** Creates a task from a user command. */
    public Task parseTask(String command) throws FeinException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            return parseTodo(command);
        }
        if (command.equals("deadline") || command.startsWith("deadline ")) {
            return parseDeadline(command);
        }
        if (command.equals("event") || command.startsWith("event ")) {
            return parseEvent(command);
        }
        throw new FeinException("OOPS!!! Fein don't know that one, try again");
    }

    /** Parses a todo command into a task. */
    private Task parseTodo(String command) throws FeinException {
        String description = getRemainder(command, "todo");
        if (description.isEmpty()) {
            throw new FeinException("OOPS!!! Can't fein for nothing, give the todo a description");
        }
        return new Todo(description);
    }

    /** Parses a deadline command into a task. */
    private Task parseDeadline(String command) throws FeinException {
        String remainder = getRemainder(command, "deadline");
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

    /** Parses an event command into a task. */
    private Task parseEvent(String command) throws FeinException {
        String remainder = getRemainder(command, "event");
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

    /** Returns the text after a command keyword. */
    private String getRemainder(String command, String keyword) {
        return command.length() > keyword.length()
                ? command.substring(keyword.length()).trim() : "";
    }

    /** Parses a mark, unmark, or delete command's task number. */
    public int parseTaskNumber(String command, String action) throws FeinException {
        String value = command.length() > action.length()
                ? command.substring(action.length()).trim() : "";
        if (value.isEmpty()) {
            String actionName = action.substring(0, 1).toUpperCase() + action.substring(1);
            throw new FeinException("OOPS!!! " + actionName + " what? Give Fein a task number");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new FeinException("OOPS!!! That's not a number, Fein can't read minds");
        }
    }

    /** Returns the keyword from a find command. */
    public String parseFindKeyword(String command) throws FeinException {
        String keyword = command.length() > "find".length()
                ? command.substring("find".length()).trim() : "";
        if (keyword.isEmpty()) {
            throw new FeinException("OOPS!!! Find what? Give Fein a keyword");
        }
        return keyword;
    }
}
