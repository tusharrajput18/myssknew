package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.UserLoginModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class CandidateLoginResponse extends Entity {
    private List<UserLoginModel> data;

    public List<UserLoginModel> getData() {
        return data;
    }
}