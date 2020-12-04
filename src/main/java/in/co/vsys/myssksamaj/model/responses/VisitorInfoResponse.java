package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;

/**
 * @author abhijeetjadhav
 */
public class VisitorInfoResponse extends Entity {
    private List<UserProfileModel> data;

    public List<UserProfileModel> getData() {
        return data;
    }
}