package in.co.vsys.myssksamaj.model;

import java.util.Date;
import java.util.HashMap;

public class FirebaseModel {

    public String photoUrl;
    public String name;
    public String firstname;
    public String lastname;
    private String photoThumb;
    private String uid;
    private String geofire;
    public String fcmUserDeviceId;
    private int countViews;
    private long timestamp;
    private long lastseenView;
    private Date lastActive;
    private HashMap<String, Object> chat;

    private int status;
    public boolean online;


    public FirebaseModel() {
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhotoThumb() {
        return photoThumb;
    }

    public void setPhotoThumb(String photoThumb) {
        this.photoThumb = photoThumb;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGeofire() {
        return geofire;
    }

    public void setGeofire(String geofire) {
        this.geofire = geofire;
    }

    public int getCountViews() {
        return countViews;
    }

    public void setCountViews(int countViews) {
        this.countViews = countViews;
    }

    public long getLastseenView() {
        return lastseenView;
    }

    public void setLastseenView(long lastseenView) {
        this.lastseenView = lastseenView;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public String getFcmUserDeviceId() {
        return fcmUserDeviceId;
    }

    public void setFcmUserDeviceId(String fcmUserDeviceId) {
        this.fcmUserDeviceId = fcmUserDeviceId;
    }

    public HashMap<String, Object> getChat() {
        return chat;
    }

    public void setChat(HashMap<String, Object> chat) {
        this.chat = chat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
