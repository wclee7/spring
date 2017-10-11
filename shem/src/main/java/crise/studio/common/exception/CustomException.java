package crise.studio.common.exception;

public class CustomException extends RuntimeException {

    protected String code;

    protected StackTraceElement[] storedStackTraceElements;

    public CustomException() {
        super();
    }

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public CustomException(String msg, String code, StackTraceElement[] elements) {
        super(msg);
        this.code = code;
        this.storedStackTraceElements = elements;
    }

    public String getErrorCode() {
        return this.code;
    }

}
