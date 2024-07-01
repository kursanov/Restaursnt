package kursanov.excaptions;

public class AlreadyException extends RuntimeException{
    public AlreadyException() {
    }

    public AlreadyException(String message) {
        super(message);
    }
}
