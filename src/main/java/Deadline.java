import java.time.LocalDateTime;

/** A task that must be completed before a specified date or time. */
public class Deadline extends Task {
    /** The parsed deadline, when the user supplied a supported date and time. */
    private final LocalDateTime by;

    /** The original text for natural-language deadlines that cannot be parsed. */
    private final String originalBy;

    /** Creates an incomplete deadline task. */
    public Deadline(String description, String by) {
        super(description);
        this.by = DateTimeParser.parse(by);
        this.originalBy = by;
    }

    /** Returns the parsed deadline, or {@code null} for natural-language text. */
    public LocalDateTime getBy() {
        return by;
    }

    /** Returns the deadline in the form that should be displayed and saved. */
    public String getByText() {
        return by == null ? originalBy : DateTimeParser.format(by);
    }

    /** Returns this deadline with its type and deadline markers. */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByText() + ")";
    }
}
