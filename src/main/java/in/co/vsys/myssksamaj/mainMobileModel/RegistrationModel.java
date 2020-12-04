package in.co.vsys.myssksamaj.mainMobileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegistrationModel {

    @SerializedName("id")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<RegistrationDetailsModel> registrationDetailsModels ;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<RegistrationDetailsModel> getRegistrationDetailsModels() {
        return registrationDetailsModels;
    }

    public void setRegistrationDetailsModels(ArrayList<RegistrationDetailsModel> registrationDetailsModels) {
        this.registrationDetailsModels = registrationDetailsModels;
    }
}
