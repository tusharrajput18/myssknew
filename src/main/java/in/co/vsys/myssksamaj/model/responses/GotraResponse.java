package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.GotraModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class GotraResponse extends Entity {
private List<GotraModel> data;

    public List<GotraModel> getGotraModels() {
        return data;
    }
}