package fein;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests Fein's command-processing responses used by the graphical interface. */
class FeinTest {
    /** Verifies that GUI responses use the same commands and task operations as the CLI. */
    @Test
    void getResponseProcessesExistingCliCommands(@TempDir Path temporaryDirectory) {
        Fein fein = new Fein(temporaryDirectory.resolve("fein.txt").toString());

        assertTrue(fein.getResponse("todo read book").contains("read book"));
        assertEquals("add", fein.getCommandType());
        assertTrue(fein.getResponse("list").contains("[T][ ] read book"));
        assertEquals("list", fein.getCommandType());
        assertTrue(fein.getResponse("mark 1").contains("[T][X] read book"));
        assertEquals("mark", fein.getCommandType());
        assertTrue(fein.getResponse("find BOOK").contains("read book"));
        assertEquals("find", fein.getCommandType());
        assertTrue(fein.getResponse("delete 1").contains("read book"));
        assertEquals("delete", fein.getCommandType());
    }

    /** Verifies that invalid commands return the existing CLI error and error style type. */
    @Test
    void getResponseReportsInvalidCommands(@TempDir Path temporaryDirectory) {
        Fein fein = new Fein(temporaryDirectory.resolve("fein.txt").toString());

        assertEquals("OOPS!!! Fein don't know that one, try again", fein.getResponse("unknown"));
        assertEquals("error", fein.getCommandType());
    }
}
