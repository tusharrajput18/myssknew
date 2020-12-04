package in.co.vsys.myssksamaj.newsmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectCummentsNewsId {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<AllNewsCumments> allNewsCummentsArrayList;

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

    public ArrayList<AllNewsCumments> getAllNewsCummentsArrayList() {
        return allNewsCummentsArrayList;
    }

    public void setAllNewsCummentsArrayList(ArrayList<AllNewsCumments> allNewsCummentsArrayList) {
        this.allNewsCummentsArrayList = allNewsCummentsArrayList;
    }
}
