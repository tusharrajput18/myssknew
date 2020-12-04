package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SellectAllBusinessType {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<BusinessModules> businessModulesList;

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

    public ArrayList<BusinessModules> getBusinessModulesList() {
        return businessModulesList;
    }

    public void setBusinessModulesList(ArrayList<BusinessModules> businessModulesList) {
        this.businessModulesList = businessModulesList;
    }

    public class BusinessModules {


        @SerializedName("businessTypeId")
        @Expose
        private String businessTypeId;

        @SerializedName("businessType")
        @Expose
        private String businessType;

        @SerializedName("Column1")
        @Expose
        private String business_image;

        @SerializedName("ispopular")
        @Expose
        private String ispopular;

        public String getBusinessTypeId() {
            return businessTypeId;
        }

        public void setBusinessTypeId(String businessTypeId) {
            this.businessTypeId = businessTypeId;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getBusiness_image() {
            return business_image;
        }

        public void setBusiness_image(String business_image) {
            this.business_image = business_image;
        }

        public String getIspopular() {
            return ispopular;
        }

        public void setIspopular(String ispopular) {
            this.ispopular = ispopular;
        }
    }
}
