package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.CountryModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class CountryResponse extends Entity {

    private List<CountryModel> data;

    public List<CountryModel> getData() {
        return data;
    }
}