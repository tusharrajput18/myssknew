package in.co.vsys.myssksamaj.model.responses;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.model.data_models.DistanceModel;

public class GetDistanceResponse extends Entity {
    ArrayList<DistanceModel> data = new ArrayList<>();

    public ArrayList<DistanceModel> getData() {
        return data;
    }

    public void setData(ArrayList<DistanceModel> data) {
        this.data = data;
    }
}
