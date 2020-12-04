package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.StateModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class StateResponse extends Entity {
    private List<StateModel> data;

    public List<StateModel> getData() {
        return data;
    }
}