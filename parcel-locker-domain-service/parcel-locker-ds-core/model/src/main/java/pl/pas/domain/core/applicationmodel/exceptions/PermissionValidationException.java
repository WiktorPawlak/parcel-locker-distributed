package pl.pas.domain.core.applicationmodel.exceptions;

public class PermissionValidationException extends RuntimeException {

    public PermissionValidationException(final String message) {
        super(message);
    }
}
