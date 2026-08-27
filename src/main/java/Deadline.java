/** A task that must be completed before a specified date or time. */
public class Deadline extends Task {
    /** The user-provided deadline, kept as text. */
    private final String by;

    /** Creates an incomplete deadline task. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toDisplayString() {
        return super.toDisplayString() + " (by: " + by + ")";
    }
}
