package in.co.vsys.myssksamaj.newsmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllNewsCumments {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("commenttext")
    @Expose
    private String commenttext;

    @SerializedName("createddate")
    @Expose
    private String createddate;

    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("fname")
    @Expose
    private String fname;

    @SerializedName("mname")
    @Expose
    private String mname;

    @SerializedName("lname")
    @Expose
    private String lname;

    @SerializedName("profileimage")
    @Expose
    private String profileimage;

    @SerializedName("newsid")
    @Expose
    private String newsid;

    @SerializedName("isdeleted")
    @Expose
    private String isdeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }
}
