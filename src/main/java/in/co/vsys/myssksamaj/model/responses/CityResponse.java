package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.CityModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class CityResponse extends Entity {
    private List<CityModel> data;

    public List<CityModel> getData() {
        return data;
    }
}