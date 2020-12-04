package in.co.vsys.myssksamaj.model.responses;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.model.data_models.OccupationModel;

public class AllOccupationResponse extends Entity {

    ArrayList<OccupationModel> data=new ArrayList<>();

    public ArrayList<OccupationModel> getData() {
        return data;
    }

    public void setData(ArrayList<OccupationModel> data) {
        this.data = data;
    }
}
