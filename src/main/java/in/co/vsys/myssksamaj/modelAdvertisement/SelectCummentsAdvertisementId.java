package in.co.vsys.myssksamaj.modelAdvertisement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.newsmodels.AllNewsCumments;

public class SelectCummentsAdvertisementId {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AllAdvertisementCumments> allAdvertisementCummentsArrayList;


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

    public ArrayList<AllAdvertisementCumments> getAllAdvertisementCummentsArrayList() {
        return allAdvertisementCummentsArrayList;
    }

    public void setAllAdvertisementCummentsArrayList(ArrayList<AllAdvertisementCumments> allAdvertisementCummentsArrayList) {
        this.allAdvertisementCummentsArrayList = allAdvertisementCummentsArrayList;
    }
}
