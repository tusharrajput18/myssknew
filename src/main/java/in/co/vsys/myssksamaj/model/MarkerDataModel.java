package in.co.vsys.myssksamaj.model;

public class MarkerDataModel {

    private double latitude;
    private double longitude;
    private String username;
    private String userImage;
    private String uniqueId;
    private double distance;
    private String markerDetails;
    private String colorIcon;
    private String marriedstatus;

    public MarkerDataModel() {
    }

    public MarkerDataModel(double latitude, double longitude, String username) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.username = username;
    }

    public String getMarriedstatus() {
        return marriedstatus;
    }

    public void setMarriedstatus(String marriedstatus) {
        this.marriedstatus = marriedstatus;
    }

    public String getColorIcon() {
        return colorIcon;
    }

    public void setColorIcon(String colorIcon) {
        this.colorIcon = colorIcon;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getMarkerDetails() {
        return markerDetails;
    }

    public void setMarkerDetails(String markerDetails) {
        this.markerDetails = markerDetails;
    }
}
