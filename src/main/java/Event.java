/** A task with a specified start and end date or time. */
public class Event extends Task {
    /** The user-provided start and end values, kept as text. */
    private final String from;
    private final String to;

    /** Creates an incomplete event task. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toDisplayString() {
        return super.toDisplayString() + " (from: " + from + " to: " + to + ")";
    }
}
