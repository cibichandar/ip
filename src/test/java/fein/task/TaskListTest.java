package fein.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fein.FeinException;

/** Tests task deletion and task-number validation. */
class TaskListTest {
    /** Verifies that deleting a task shifts later tasks into the correct positions. */
    @Test
    void deleteRemovesCorrectTaskAndShiftsRemainingTasks() throws FeinException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("first"));
        tasks.add(new Todo("second"));
        tasks.add(new Todo("third"));

        Task deleted = tasks.delete(2);

        assertEquals("second", deleted.getDescription());
        assertEquals(2, tasks.size());
        assertEquals("first", tasks.get(0).getDescription());
        assertEquals("third", tasks.get(1).getDescription());
    }

    /** Verifies that zero and out-of-range task numbers are rejected. */
    @Test
    void deleteInvalidTaskNumberThrowsException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("first"));

        assertThrows(FeinException.class, () -> tasks.delete(0));
        assertThrows(FeinException.class, () -> tasks.delete(2));
    }
}
