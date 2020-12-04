package in.co.vsys.myssksamaj.model.responses;

import java.io.Serializable;

/**
 * @author Abhijeet.j
 */
public class Entity implements Serializable {
    private int success;
    private String error, message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}