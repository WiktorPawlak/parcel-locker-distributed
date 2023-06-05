package pl.pas.domain.infrastructure.exceptions;

public class LockerManagerException extends RuntimeException {
    public LockerManagerException(String message) {
        super(message);
    }

    public LockerManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
