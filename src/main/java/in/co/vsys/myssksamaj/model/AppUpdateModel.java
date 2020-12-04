package in.co.vsys.myssksamaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppUpdateModel {
    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<VersionList> versionLists;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VersionList> getVersionLists() {
        return versionLists;
    }

    public void setVersionLists(List<VersionList> versionLists) {
        this.versionLists = versionLists;
    }

    public class VersionList {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("versionnumber")
        @Expose
        private String versionnumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVersionnumber() {
            return versionnumber;
        }

        public void setVersionnumber(String versionnumber) {
            this.versionnumber = versionnumber;
        }
    }
}
