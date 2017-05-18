package model.exceptions;

public class IllegalStrategyException extends RuntimeException {
    private static final long serialVersionUID = 6725071153940393796L;

    public IllegalStrategyException() {
    }

    public IllegalStrategyException(String message) {
        super(message);
    }

}
