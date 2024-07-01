package kursanov.excaptions;

public class BedRequestException extends RuntimeException{
    public BedRequestException() {
        super();
    }

    public BedRequestException(String message) {
        super(message);
    }
}
