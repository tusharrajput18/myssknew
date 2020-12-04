package in.co.vsys.myssksamaj.newsmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class AllNewsTypeModel {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AllNewsType> allNewsTypeArrayList;

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

    public ArrayList<AllNewsType> getAllNewsTypeArrayList() {
        return allNewsTypeArrayList;
    }

    public void setAllNewsTypeArrayList(ArrayList<AllNewsType> allNewsTypeArrayList) {
        this.allNewsTypeArrayList = allNewsTypeArrayList;
    }

    public class AllNewsType {

        @SerializedName("NewsTypeId")
        @Expose
        private String NewsTypeId;

        @SerializedName("NewsType")
        @Expose
        private String NewsType;

        public String getNewsTypeId() {
            return NewsTypeId;
        }

        public void setNewsTypeId(String newsTypeId) {
            NewsTypeId = newsTypeId;
        }

        public String getNewsType() {
            return NewsType;
        }

        public void setNewsType(String newsType) {
            NewsType = newsType;
        }
    }
}
