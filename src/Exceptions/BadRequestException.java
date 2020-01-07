package Exceptions;

public class BadRequestException extends Exception {
    public BadRequestException(String msg) {
        super(msg);
    }
}
