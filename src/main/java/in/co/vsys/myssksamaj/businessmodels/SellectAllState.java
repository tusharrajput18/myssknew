package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SellectAllState {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AllStateList> allStateLists;

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

    public ArrayList<AllStateList> getAllStateLists() {
        return allStateLists;
    }

    public void setAllStateLists(ArrayList<AllStateList> allStateLists) {
        this.allStateLists = allStateLists;
    }

    public class AllStateList {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("statename")
        @Expose
        private String statename;

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

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
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
