package fein.task;

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

    /** Returns the user-provided event start text. */
    public String getFrom() {
        return from;
    }

    /** Returns the user-provided event end text. */
    public String getTo() {
        return to;
    }

    /** Returns this event with its type and time-range markers. */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
