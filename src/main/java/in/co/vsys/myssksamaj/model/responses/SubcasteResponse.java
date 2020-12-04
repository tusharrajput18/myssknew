package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.SubcasteModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class SubcasteResponse extends Entity {
    private List<SubcasteModel> data;

    public List<SubcasteModel> getData() {
        return data;
    }
}