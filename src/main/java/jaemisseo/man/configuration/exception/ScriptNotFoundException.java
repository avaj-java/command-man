package jaemisseo.man.configuration.exception;

public class ScriptNotFoundException extends Exception {

    ScriptNotFoundException() {
    }

    ScriptNotFoundException(String message) {
        super(message);
    }

    ScriptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    ScriptNotFoundException(Throwable cause) {
        super(cause);
    }

}
