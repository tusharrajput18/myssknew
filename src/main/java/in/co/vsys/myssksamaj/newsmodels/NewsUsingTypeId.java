package in.co.vsys.myssksamaj.newsmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsUsingTypeId implements Serializable {

    @SerializedName("Newsid")
    @Expose
    private String Newsid;

    @SerializedName("apploginid")
    @Expose
    private String apploginid;

    @SerializedName("NewsType")
    @Expose
    private String NewsType;

    @SerializedName("shortdescription")
    @Expose
    private String shortdescription;

    @SerializedName("Image")
    @Expose
    private String Image;

    @SerializedName("Image2")
    @Expose
    private String Image2;

    @SerializedName("Image3")
    @Expose
    private String Image3;

    @SerializedName("Image4")
    @Expose
    private String Image4;

    @SerializedName("Image5")
    @Expose
    private String Image5;

    @SerializedName("longdescription")
    @Expose
    private String longdescription;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("NewsStatus")
    @Expose
    private String NewsStatus;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("profileimage")
    @Expose
    private String profileimage;

    @SerializedName("liked")
    @Expose
    private String liked;

    @SerializedName("likedcount")
    @Expose
    private String likedcount;

    @SerializedName("commentcount")
    @Expose
    private String commentcount;

    public String getLikedcount() {
        return likedcount;
    }

    public void setLikedcount(String likedcount) {
        this.likedcount = likedcount;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getNewsid() {
        return Newsid;
    }

    public void setNewsid(String newsid) {
        Newsid = newsid;
    }

    public String getApploginid() {
        return apploginid;
    }

    public void setApploginid(String apploginid) {
        this.apploginid = apploginid;
    }

    public String getNewsType() {
        return NewsType;
    }

    public void setNewsType(String newsType) {
        NewsType = newsType;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getImage4() {
        return Image4;
    }

    public void setImage4(String image4) {
        Image4 = image4;
    }

    public String getImage5() {
        return Image5;
    }

    public void setImage5(String image5) {
        Image5 = image5;
    }

    public String getLongdescription() {
        return longdescription;
    }

    public void setLongdescription(String longdescription) {
        this.longdescription = longdescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNewsStatus() {
        return NewsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        NewsStatus = newsStatus;
    }
}
