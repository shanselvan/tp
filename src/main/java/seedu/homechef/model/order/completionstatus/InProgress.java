package seedu.homechef.model.order.completionstatus;

public class InProgress extends CompletionStatus{
    public InProgress() {
        super(0);
    }

    @Override
    public String toString() {
        return "In progress";
    }
}
