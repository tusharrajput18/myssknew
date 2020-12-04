package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class UserDetailsResponse extends Entity {
    private List<UserDetailsModel> data;

    public List<UserDetailsModel> getData() {
        return data;
    }
}