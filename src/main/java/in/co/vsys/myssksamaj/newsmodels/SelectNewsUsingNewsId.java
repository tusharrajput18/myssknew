package in.co.vsys.myssksamaj.newsmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectNewsUsingNewsId {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<NewsUsingTypeId> newsUsingTypeIdArrayList;

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

    public ArrayList<NewsUsingTypeId> getNewsUsingTypeIdArrayList() {
        return newsUsingTypeIdArrayList;
    }

    public void setNewsUsingTypeIdArrayList(ArrayList<NewsUsingTypeId> newsUsingTypeIdArrayList) {
        this.newsUsingTypeIdArrayList = newsUsingTypeIdArrayList;
    }
}
