package in.co.vsys.myssksamaj.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jack on 06/12/2017.
 */

public class CityModel {

    @SerializedName("id")
    private int cityId;
    @SerializedName("cityname")
    private String cityName;

    public CityModel() {
    }

    public CityModel(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return getCityName();
    }
}
