package in.co.vsys.myssksamaj.modelAdvertisement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisemtnTypeList {

    @SerializedName("advertisementTypeId")
    @Expose
    private String advertisementTypeId;

    @SerializedName("advertisementType")
    @Expose
    private String advertisementType;

    public String getAdvertisementTypeId() {
        return advertisementTypeId;
    }

    public void setAdvertisementTypeId(String advertisementTypeId) {
        this.advertisementTypeId = advertisementTypeId;
    }

    public String getAdvertisementType() {
        return advertisementType;
    }

    public void setAdvertisementType(String advertisementType) {
        this.advertisementType = advertisementType;
    }
}
