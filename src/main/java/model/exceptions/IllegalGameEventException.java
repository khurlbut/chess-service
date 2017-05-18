package model.exceptions;

public class IllegalGameEventException extends IllegalArgumentException {
    private static final long serialVersionUID = -8631624273934062399L;

    public IllegalGameEventException(String message) {
        super(message);
    }

}
