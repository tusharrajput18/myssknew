package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.MotherTongueModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class MotherTongueResponse extends Entity {
    private List<MotherTongueModel> data;

    public List<MotherTongueModel> getData() {
        return data;
    }
}