/** A task with a specified start and end date or time. */
public class Event extends Task {
    /** The user-provided start and end values, kept as text. */
    protected String from;
    protected String to;

    /** Creates an incomplete event task. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Returns this event with its type and time-range markers. */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
