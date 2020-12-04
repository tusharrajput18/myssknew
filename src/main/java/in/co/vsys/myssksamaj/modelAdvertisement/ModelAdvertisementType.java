package in.co.vsys.myssksamaj.modelAdvertisement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelAdvertisementType {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AdvertisemtnTypeList> advertisemtnTypeLists;

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

    public ArrayList<AdvertisemtnTypeList> getAdvertisemtnTypeLists() {
        return advertisemtnTypeLists;
    }

    public void setAdvertisemtnTypeLists(ArrayList<AdvertisemtnTypeList> advertisemtnTypeLists) {
        this.advertisemtnTypeLists = advertisemtnTypeLists;
    }
}
