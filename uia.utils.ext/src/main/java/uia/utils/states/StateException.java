package uia.utils.states;

public class StateException extends RuntimeException {

    private static final long serialVersionUID = 8187383561451669713L;

    public StateException(String message) {
        super(message);
    }

    public StateException(String message, Throwable th) {
        super(message, th);
    }
}
