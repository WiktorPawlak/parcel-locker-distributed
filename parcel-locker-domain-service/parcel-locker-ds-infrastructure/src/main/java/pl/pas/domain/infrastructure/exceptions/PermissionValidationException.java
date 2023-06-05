package pl.pas.domain.infrastructure.exceptions;

public class PermissionValidationException extends RuntimeException {

    public PermissionValidationException(final String message) {
        super(message);
    }
}
