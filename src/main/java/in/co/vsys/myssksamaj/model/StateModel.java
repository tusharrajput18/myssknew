package in.co.vsys.myssksamaj.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jack on 06/12/2017.
 */
public class StateModel {

    @SerializedName("id")
    private int stateId;

    @SerializedName("statename")
    private String stateName;

    public StateModel() {
    }

    public StateModel(int stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return getStateName();
    }
}
