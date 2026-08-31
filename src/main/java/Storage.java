import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Saves and loads Fein's task list using a relative text-file path. */
public class Storage {
    /** The file used for Fein's saved task list. */
    private static final Path TASK_FILE = Path.of("data", "duke.txt");

    /**
     * Loads valid saved tasks into the supplied array.
     *
     * <p>A missing file represents an empty task list. Blank and malformed lines are ignored so
     * one damaged record does not prevent the rest of the list from being loaded.</p>
     *
     * @param tasks the array that receives loaded tasks
     * @return the number of tasks loaded
     * @throws FeinException if the file exists but cannot be read
     */
    public static int loadTasks(Task[] tasks) throws FeinException {
        try {
            if (!Files.exists(TASK_FILE)) {
                return 0;
            }
        } catch (SecurityException exception) {
            throw new FeinException("OOPS!!! Fein couldn't load your saved tasks");
        }

        try {
            List<String> lines = Files.readAllLines(TASK_FILE, StandardCharsets.UTF_8);
            int taskCount = 0;
            for (String line : lines) {
                if (taskCount == tasks.length) {
                    break;
                }
                Task task = parseTask(line);
                if (task != null) {
                    tasks[taskCount] = task;
                    taskCount++;
                }
            }
            return taskCount;
        } catch (IOException | SecurityException exception) {
            throw new FeinException("OOPS!!! Fein couldn't load your saved tasks");
        }
    }

    /**
     * Replaces the saved task list with the tasks currently in memory.
     *
     * @param tasks the array containing the current tasks
     * @param taskCount the number of valid tasks in the array
     * @throws FeinException if the task list cannot be written
     */
    public static void saveTasks(Task[] tasks, int taskCount) throws FeinException {
        try {
            Files.createDirectories(TASK_FILE.getParent());
            StringBuilder savedTasks = new StringBuilder();
            for (int i = 0; i < taskCount; i++) {
                savedTasks.append(formatTask(tasks[i])).append(System.lineSeparator());
            }
            Files.writeString(TASK_FILE, savedTasks.toString(), StandardCharsets.UTF_8);
        } catch (IOException | SecurityException exception) {
            throw new FeinException("OOPS!!! Fein couldn't save your tasks");
        }
    }

    /** Converts one task into the simple line format used by the save file. */
    private static String formatTask(Task task) {
        String type;
        String details = task.getDescription();
        if (task instanceof Deadline deadline) {
            type = "D";
            details += " | " + deadline.getBy();
        } else if (task instanceof Event event) {
            type = "E";
            details += " | " + event.getFrom() + " | " + event.getTo();
        } else {
            type = "T";
        }
        return type + " | " + (task.isDone() ? "1" : "0") + " | " + details;
    }

    /** Converts one saved line into a task, or returns null for a malformed line. */
    private static Task parseTask(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3 || !(fields[1].equals("0") || fields[1].equals("1"))) {
            return null;
        }

        String description = fields[2].trim();
        if (description.isEmpty()) {
            return null;
        }

        Task task;
        if (fields[0].equals("T") && fields.length == 3) {
            task = new Todo(description);
        } else if (fields[0].equals("D") && fields.length == 4 && !fields[3].isBlank()) {
            task = new Deadline(description, fields[3].trim());
        } else if (fields[0].equals("E") && fields.length == 5
                && !fields[3].isBlank() && !fields[4].isBlank()) {
            task = new Event(description, fields[3].trim(), fields[4].trim());
        } else {
            return null;
        }

        if (fields[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
