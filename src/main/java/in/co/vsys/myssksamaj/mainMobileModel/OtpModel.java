package in.co.vsys.myssksamaj.mainMobileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OtpModel {

    @SerializedName("id")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<DetailOtpModel> detailOtpModels;

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

    public ArrayList<DetailOtpModel> getDetailOtpModels() {
        return detailOtpModels;
    }

    public void setDetailOtpModels(ArrayList<DetailOtpModel> detailOtpModels) {
        this.detailOtpModels = detailOtpModels;
    }
}
