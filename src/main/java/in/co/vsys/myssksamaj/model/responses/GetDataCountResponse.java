package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.MyDataCountModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class GetDataCountResponse extends Entity {
    private List<MyDataCountModel> data;

    public List<MyDataCountModel> getData() {
        return data;
    }
}