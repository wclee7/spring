package crise.studio.model.VO;

public class ErrorInformationVO {

    private String errorCode;

    private String message;

    public ErrorInformationVO() {
    }

    public ErrorInformationVO(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "ErrorInformationVO{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
