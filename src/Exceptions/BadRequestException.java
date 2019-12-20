package Exceptions;

public class BadRequestException extends Exception { //code 400
    public BadRequestException(String msg) { //to co w notfoundexc
        super(msg);
    }
}
