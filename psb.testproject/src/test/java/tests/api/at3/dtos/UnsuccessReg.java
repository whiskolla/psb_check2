package tests.api.at3.dtos;

public class UnsuccessReg {
    private String error;
    private String timestamp;
    private String path;
    private String status;
    private String message;

    public UnsuccessReg(String timestamp, String status, String error, String path) {
        this.error = error;
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
    }

    public UnsuccessReg(String message, String status) {
        this.status = status;
        this.message = message;
    }

    public UnsuccessReg(String error) {
        this.error = error;
    }

    public UnsuccessReg() {}

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
