package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class SearchByUniqueIdResponse extends Entity {
    private List<UserProfileModel> data;

    public List<UserProfileModel> getData() {
        return data;
    }
}