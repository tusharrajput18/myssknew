package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 02/12/2017.
 */

public class EductionModel {

    private String eductionMaster;
    private int eductionId;

    public EductionModel() {
    }

    public EductionModel(String eductionMaster) {
        this.eductionMaster = eductionMaster;
    }

    public EductionModel(int eductionId, String eductionMaster) {
        this.eductionId = eductionId;
        this.eductionMaster = eductionMaster;
    }

    public String getEductionMaster() {
        return eductionMaster;
    }

    public void setEductionMaster(String eductionMaster) {
        this.eductionMaster = eductionMaster;
    }

    public int getEductionId() {
        return eductionId;
    }

    public void setEductionId(int eductionId) {
        this.eductionId = eductionId;
    }

    @Override
    public String toString() {
        return getEductionMaster();
    }
}
