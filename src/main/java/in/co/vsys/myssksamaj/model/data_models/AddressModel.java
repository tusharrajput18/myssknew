package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

public class AddressModel implements Serializable {
    private String AddressId;
    private String Address;
    private String Landmark;
    private String Locality;
    private String City;
    private String State;
    private String Country;
    private String Latitude;
    private String Longitude;
    private String Pincode;
    private String Distance;
    private String EntityTypeId;
    private String EntityId;
    private int GeoLocationId = 0;
    private int AddressTypeId = 0;

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    public final String getAddress() {
        return this.Address;
    }

    public final void setAddress(String var1) {
        this.Address = var1;
    }

    public final String getLandmark() {
        return this.Landmark;
    }

    public final void setLandmark(String var1) {
        this.Landmark = var1;
    }

    public final String getLocality() {
        return this.Locality;
    }

    public final void setLocality(String var1) {
        this.Locality = var1;
    }

    public final String getCity() {
        return this.City;
    }

    public final void setCity(String var1) {
        this.City = var1;
    }

    public final String getState() {
        return this.State;
    }

    public final void setState(String var1) {
        this.State = var1;
    }

    public final String getCountry() {
        return this.Country;
    }

    public final void setCountry(String var1) {
        this.Country = var1;
    }

    public final String getLatitude() {
        return this.Latitude;
    }

    public final void setLatitude(String var1) {
        this.Latitude = var1;
    }

    public final String getLongitude() {
        return this.Longitude;
    }

    public final void setLongitude(String var1) {
        this.Longitude = var1;
    }

    public final String getPincode() {
        return this.Pincode;
    }

    public final void setPincode(String var1) {
        this.Pincode = var1;
    }

    public final String getDistance() {
        return this.Distance;
    }

    public final void setDistance(String var1) {
        this.Distance = var1;
    }

    public final String getEntityTypeId() {
        return this.EntityTypeId;
    }

    public final String getEntityId() {
        return this.EntityId;
    }

    public final int getAddressTypeId() {
        return this.AddressTypeId;
    }

    public void setEntityTypeId(String entityTypeId) {
        EntityTypeId = entityTypeId;
    }

    public void setEntityId(String entityId) {
        EntityId = entityId;
    }

    public void setAddressTypeId(int addressTypeId) {
        AddressTypeId = addressTypeId;
    }

    public int getGeoLocationId() {
        return GeoLocationId;
    }

    public void setGeoLocationId(int geoLocationId) {
        GeoLocationId = geoLocationId;
    }
}