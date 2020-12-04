package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 04/12/2017.
 */

public class SpinnerGropModel {


    public SpinnerGropModel() {
    }

    public SpinnerGropModel(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    public String getMarriedStatus() {
        return marriedStatus;
    }

    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    private String marriedStatus;
}
