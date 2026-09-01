import java.util.ArrayList;
import java.util.List;

/** Owns Fein's tasks and operations that change the task list. */
public class TaskList {
    /** The tasks currently managed by Fein. */
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> initialTasks) {
        tasks = new ArrayList<>(initialTasks);
    }

    /** Adds a task to the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Returns the task at a zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    /** Returns whether there are no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Removes and returns the task at a one-based task number. */
    public Task delete(int taskNumber) throws FeinException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /** Marks the task at a one-based task number as done. */
    public Task mark(int taskNumber) throws FeinException {
        validateTaskNumber(taskNumber);
        Task task = tasks.get(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    /** Marks the task at a one-based task number as not done. */
    public Task unmark(int taskNumber) throws FeinException {
        validateTaskNumber(taskNumber);
        Task task = tasks.get(taskNumber - 1);
        task.markAsNotDone();
        return task;
    }

    /** Returns a defensive copy for persistence. */
    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }

    /** Checks that a one-based task number refers to an existing task. */
    private void validateTaskNumber(int taskNumber) throws FeinException {
        if (taskNumber <= 0) {
            throw new FeinException("OOPS!!! Task numbers start from 1, not 0");
        }
        if (taskNumber > tasks.size()) {
            throw new FeinException("OOPS!!! Task " + taskNumber
                    + " don't exist, check your list again");
        }
    }
}
