package in.co.vsys.myssksamaj.businessmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommonUserModel {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<CommonUserModelList> commonUserModelLists;

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

    public ArrayList<CommonUserModelList> getCommonUserModelLists() {
        return commonUserModelLists;
    }

    public void setCommonUserModelLists(ArrayList<CommonUserModelList> commonUserModelLists) {
        this.commonUserModelLists = commonUserModelLists;
    }

    public class CommonUserModelList {

        @SerializedName("AppLoginId")
        @Expose
        private String AppLoginId;

        @SerializedName("FirstName")
        @Expose
        private String FirstName;

        @SerializedName("Middle")
        @Expose
        private String Middle;

        @SerializedName("LName")
        @Expose
        private String LName;

        @SerializedName("Mobile")
        @Expose
        private String Mobile;

        @SerializedName("isactive")
        @Expose
        private String isactive;

        public String getAppLoginId() {
            return AppLoginId;
        }

        public void setAppLoginId(String appLoginId) {
            AppLoginId = appLoginId;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getMiddle() {
            return Middle;
        }

        public void setMiddle(String middle) {
            Middle = middle;
        }

        public String getLName() {
            return LName;
        }

        public void setLName(String LName) {
            this.LName = LName;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getIsactive() {
            return isactive;
        }

        public void setIsactive(String isactive) {
            this.isactive = isactive;
        }
    }
}
