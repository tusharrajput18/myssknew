package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 06/12/2017.
 */

public class EductionInModel {


    private int eductionINId;
    private String eductionIn;


    public EductionInModel() {
    }

    public EductionInModel( int eductionINId,String eductionIn) {
        this.eductionINId = eductionINId;
        this.eductionIn = eductionIn;
    }

    public String getEductionIn() {
        return eductionIn;
    }

    public void setEductionIn(String eductionIn) {
        this.eductionIn = eductionIn;
    }

    public int getEductionINId() {
        return eductionINId;
    }

    public void setEductionINId(int eductionINId) {
        this.eductionINId = eductionINId;
    }

    @Override
    public String toString() {
        return getEductionIn();
    }
}
