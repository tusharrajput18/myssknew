package in.co.vsys.myssksamaj.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jack on 02/12/2017.
 */

public class CountryModel  {

    @SerializedName("countrycode")
    private int countryId;
    @SerializedName("countryname")
    private String countryName;
    private String tmp;

    public CountryModel() {
    }

    public CountryModel(String countryName) {
        this.countryName = countryName;
    }

    public CountryModel(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public CountryModel(int countryId, String tmp,String countryName) {
        this.countryId = countryId;
        this.tmp = tmp;
        this.countryName = countryName;
    }


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }



    @Override
    public String toString() {
        return getCountryName();
    }


}
