package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectServicesUsingSubCategoryId {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<SellectAllServices> allServicesArrayList;

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

    public ArrayList<SellectAllServices> getAllServicesArrayList() {
        return allServicesArrayList;
    }

    public void setAllServicesArrayList(ArrayList<SellectAllServices> allServicesArrayList) {
        this.allServicesArrayList = allServicesArrayList;
    }

    public class SellectAllServices {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("subcategoryid")
        @Expose
        private String subcategoryid;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("isactive")
        @Expose
        private String isactive;

        @SerializedName("isdeleted")
        @Expose
        private String isdeleted;

        boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubcategoryid() {
            return subcategoryid;
        }

        public void setSubcategoryid(String subcategoryid) {
            this.subcategoryid = subcategoryid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsactive() {
            return isactive;
        }

        public void setIsactive(String isactive) {
            this.isactive = isactive;
        }

        public String getIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(String isdeleted) {
            this.isdeleted = isdeleted;
        }
    }
}
