package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.UserMatchModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class UserMatchResponse extends Entity {
    private List<UserMatchModel> data;

    public List<UserMatchModel> getData() {
        return data;
    }

    public void setData(List<UserMatchModel> data) {
        this.data = data;
    }
}