package models;

/**
 * Created by attribe on 6/9/16.
 */
public class OrderResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    String message;
    int status;

}
