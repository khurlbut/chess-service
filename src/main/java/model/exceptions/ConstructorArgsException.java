package model.exceptions;

public class ConstructorArgsException extends IllegalArgumentException {
    private static final long serialVersionUID = 8704140456486202229L;

    public ConstructorArgsException(String message) {
        super(message);
    }

}
