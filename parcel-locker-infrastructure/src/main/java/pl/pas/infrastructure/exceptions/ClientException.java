package pl.pas.infrastructure.exceptions;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}
