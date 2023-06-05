package pl.pas.domain.core.applicationmodel.exceptions;

public class LockerManagerException extends RuntimeException {
    public LockerManagerException(String message) {
        super(message);
    }

    public LockerManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
