package jaemisseo.man.configuration.exception;

public class WantToRestartException extends Exception {

    WantToRestartException() {
    }

    WantToRestartException(String message) {
        super(message);
    }

    WantToRestartException(String message, Throwable cause) {
        super(message, cause);
    }

    WantToRestartException(Throwable cause) {
        super(cause);
    }

}
