package pl.pas.infrastructure.exceptions;

public class PermissionValidationException extends RuntimeException {

    public PermissionValidationException(final String message) {
        super(message);
    }
}
