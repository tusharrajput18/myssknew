package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SellectAllSubCategoryType {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<SubCateList> subCateLists;

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

    public ArrayList<SubCateList> getSubCateLists() {
        return subCateLists;
    }

    public void setSubCateLists(ArrayList<SubCateList> subCateLists) {
        this.subCateLists = subCateLists;
    }

    public class SubCateList {


        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("businesscategoryid")
        @Expose
        private String businesscategoryid;

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

        public String getBusinesscategoryid() {
            return businesscategoryid;
        }

        public void setBusinesscategoryid(String businesscategoryid) {
            this.businesscategoryid = businesscategoryid;
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
