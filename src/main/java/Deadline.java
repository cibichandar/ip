/** A task that must be completed before a specified date or time. */
public class Deadline extends Task {
    /** The user-provided deadline, kept as text. */
    protected String by;

    /** Creates an incomplete deadline task. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /** Returns this deadline with its type and deadline markers. */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
