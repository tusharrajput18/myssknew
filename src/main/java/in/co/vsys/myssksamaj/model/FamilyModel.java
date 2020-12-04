package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 04/12/2017.
 */

public class FamilyModel {

    private String fatherStatus;
    private int fatherId;

    public FamilyModel() {
    }

    public FamilyModel(String fatherStatus) {
        this.fatherStatus = fatherStatus;
    }

    public String getFatherStatus() {
        return fatherStatus;
    }

    public void setFatherStatus(String fatherStatus) {
        this.fatherStatus = fatherStatus;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    @Override
    public String toString() {
        return fatherStatus;
    }
}
