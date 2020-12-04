package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;

/**
 * @author abhijeetjadhav
 */
public class SearchByNameResponse extends Entity {
    private List<UserProfileModel> data;

    public List<UserProfileModel> getData() {
        return data;
    }
}