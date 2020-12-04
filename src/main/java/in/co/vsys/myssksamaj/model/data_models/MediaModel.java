package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

public class MediaModel implements Serializable {
    private String Status;

    private String MediaCategory;

    private String MediaUrl;

    private String Date;

    private String UserId;

    private String MediaId;

    private String Title;

    private String EntityId;

    private String UserTypeId;

    private String MediaTypeId;

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setEntityId(String entityId) {
        EntityId = entityId;
    }

    public void setUserTypeId(String userTypeId) {
        UserTypeId = userTypeId;
    }

    public MediaModel() {
    }

    public MediaModel(String mediaUrl) {
        this.MediaUrl = mediaUrl;
    }

    public MediaModel(String mediaId, String mediaUrl) {
        this.MediaId = mediaId;
        this.MediaUrl = mediaUrl;
    }

    public MediaModel(String mediaId, String mediaUrl, String title) {
        this.MediaId = mediaId;
        this.MediaUrl = mediaUrl;
        this.Title = title;
    }

    public MediaModel(String mediaUrl, String title, String mediaTypeId, String mediaCategory) {
        MediaCategory = mediaCategory;
        MediaUrl = mediaUrl;
        Title = title;
        MediaTypeId = mediaTypeId;
    }

    public String getDate() {
        return this.Date;
    }

    public String getMediaId() {
        return this.MediaId;
    }

    public String getMediaUrl() {
        return this.MediaUrl;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setDate(String string2) {
        this.Date = string2;
    }

    public void setMediaId(String string2) {
        this.MediaId = string2;
    }

    public void setMediaUrl(String string2) {
        this.MediaUrl = string2;
    }

    public void setTitle(String string2) {
        this.Title = string2;
    }

    public void setMediaCategory(String mediaCategory) {
        this.MediaCategory = mediaCategory;
    }

    public void setMediaTypeId(String mediaTypeId) {
        this.MediaTypeId = mediaTypeId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMediaCategory() {
        return MediaCategory;
    }

    public String getUserId() {
        return UserId;
    }

    public String getEntityId() {
        return EntityId;
    }

    public String getUserTypeId() {
        return UserTypeId;
    }

    public String getMediaTypeId() {
        return MediaTypeId;
    }
}