package Exceptions;

public class ForbiddenMoveException extends Exception { //code 403
    public ForbiddenMoveException(String msg) { //to co w notfoundexc
        super(msg);
    }
}
