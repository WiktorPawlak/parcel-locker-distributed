package pl.pas.domain.infrastructure.exceptions;

public class RepositoryException extends RuntimeException {
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
