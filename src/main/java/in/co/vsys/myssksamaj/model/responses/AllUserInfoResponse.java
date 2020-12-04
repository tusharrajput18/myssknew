package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;

/**
 * @author abhijeetjadhav
 */
public class AllUserInfoResponse extends Entity {
    private List<UserDetailsModel> data;

    public List<UserDetailsModel> getData() {
        return data;
    }
}