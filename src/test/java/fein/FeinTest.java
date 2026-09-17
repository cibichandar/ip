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

    /** Verifies that help explains every available core command and its intended use. */
    @Test
    void getResponseExplainsAllCoreCommands(@TempDir Path temporaryDirectory) {
        Fein fein = new Fein(temporaryDirectory.resolve("fein.txt").toString());

        String helpResponse = fein.getResponse("help");

        assertTrue(helpResponse.contains("FEIN command list:"));
        assertTrue(helpResponse.contains("todo <description>"));
        assertTrue(helpResponse.contains("deadline <description> /by <due date>"));
        assertTrue(helpResponse.contains("event <description> /from <start> /to <end>"));
        assertTrue(helpResponse.contains("find <keyword>"));
        assertTrue(helpResponse.contains("mark <task number>"));
        assertTrue(helpResponse.contains("unmark <task number>"));
        assertTrue(helpResponse.contains("delete <task number>"));
        assertTrue(helpResponse.contains("Quick commands:\nlist"));
        assertTrue(helpResponse.contains("\nhelp\nPurpose: Show this command guide."));
        assertTrue(helpResponse.contains("\nbye\nPurpose: Exit Fein."));
        assertEquals("help", fein.getCommandType());
    }
}
