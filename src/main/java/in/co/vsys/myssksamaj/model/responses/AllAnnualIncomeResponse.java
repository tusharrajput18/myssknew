package in.co.vsys.myssksamaj.model.responses;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.model.data_models.AllAnnualIncomeModel;

public class AllAnnualIncomeResponse extends Entity {

    ArrayList<AllAnnualIncomeModel> data = new ArrayList<>();

    public ArrayList<AllAnnualIncomeModel> getData() {
        return data;
    }

    public void setData(ArrayList<AllAnnualIncomeModel> data) {
        this.data = data;
    }
}
