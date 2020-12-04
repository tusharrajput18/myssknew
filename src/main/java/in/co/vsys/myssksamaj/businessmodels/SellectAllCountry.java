package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SellectAllCountry {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AllCountryList> allCountryLists;

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

    public ArrayList<AllCountryList> getAllCountryLists() {
        return allCountryLists;
    }

    public void setAllCountryLists(ArrayList<AllCountryList> allCountryLists) {
        this.allCountryLists = allCountryLists;
    }

    public class AllCountryList {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("countrycode")
        @Expose
        private String countrycode;

        @SerializedName("countryname")
        @Expose
        private String countryname;

        @SerializedName("isactive")
        @Expose
        private String isactive;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
        }

        public String getCountryname() {
            return countryname;
        }

        public void setCountryname(String countryname) {
            this.countryname = countryname;
        }

        public String getIsactive() {
            return isactive;
        }

        public void setIsactive(String isactive) {
            this.isactive = isactive;
        }
    }
}
