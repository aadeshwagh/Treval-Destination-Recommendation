package csv.parser.exception;

public class NoSuchColumnException extends RuntimeException{
    public NoSuchColumnException(String message){
        super(message);
    }
}
