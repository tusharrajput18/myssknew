package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

public class OccupationModel implements Serializable {

    String occupationid;
    String occupation;

    public String getOccupationid() {
        return occupationid;
    }

    public void setOccupationid(String occupationid) {
        this.occupationid = occupationid;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
