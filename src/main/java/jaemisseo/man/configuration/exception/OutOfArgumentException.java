package jaemisseo.man.configuration.exception;

public class OutOfArgumentException extends Exception{

    OutOfArgumentException() {
    }

    OutOfArgumentException(String message) {
        super(message);
    }

    OutOfArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    OutOfArgumentException(Throwable cause) {
        super(cause);
    }

}
