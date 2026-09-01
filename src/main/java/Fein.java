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

    /** Creates Fein using the default task file. */
    public Fein() {
        this("data/fein.txt");
    }

    /** Creates Fein using the supplied task file. */
    public Fein(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FeinException exception) {
            ui.showError(exception.getMessage());
            tasks = new TaskList();
        }
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
