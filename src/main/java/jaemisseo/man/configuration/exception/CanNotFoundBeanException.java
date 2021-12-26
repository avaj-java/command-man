package jaemisseo.man.configuration.exception;

public class CanNotFoundBeanException extends Exception{

    CanNotFoundBeanException() {
    }

    CanNotFoundBeanException(String message) {
        super(message);
    }

    CanNotFoundBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    CanNotFoundBeanException(Throwable cause) {
        super(cause);
    }

}
