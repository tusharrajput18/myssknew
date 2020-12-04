package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SellectAllCity {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AllCityList> allCityLists;

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

    public ArrayList<AllCityList> getAllCityLists() {
        return allCityLists;
    }

    public void setAllCityLists(ArrayList<AllCityList> allCityLists) {
        this.allCityLists = allCityLists;
    }

    public class AllCityList {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("cityname")
        @Expose
        private String cityname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }
    }
}
