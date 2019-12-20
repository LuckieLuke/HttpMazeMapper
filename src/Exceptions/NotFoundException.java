package Exceptions;

public class NotFoundException extends Exception { //code 404
    public NotFoundException(String msg) { //tutaj możemy podawać body
        super(msg);
    }
}
