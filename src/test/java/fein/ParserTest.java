package fein;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fein.task.Task;

/** Tests the conversion of user commands into tasks. */
class ParserTest {
    /** Parser used by the test cases. */
    private final Parser parser = new Parser();

    /** Verifies that a deadline command keeps its description and date/time. */
    @Test
    void parseDeadlineCreatesCorrectTask() throws FeinException {
        Task task = parser.parseTask("deadline return book /by 2/12/2019 1800");

        assertEquals("return book", task.getDescription());
        assertEquals("[D][ ] return book (by: Dec 2 2019, 6:00 PM)", task.toString());
    }

    /** Verifies that an unrecognised command is rejected. */
    @Test
    void parseInvalidCommandThrowsException() {
        assertThrows(FeinException.class, () -> parser.parseTask("unknown command"));
    }

    /** Verifies that a find command returns its trimmed keyword. */
    @Test
    void parseFindCommandReturnsKeyword() throws FeinException {
        assertEquals("book", parser.parseFindKeyword("find book"));
    }
}
