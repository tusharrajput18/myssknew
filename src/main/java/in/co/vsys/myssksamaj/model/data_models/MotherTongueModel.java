package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

/**
 * @author abhijeetjadhav
 */
public class MotherTongueModel implements Serializable {
    private String mothertongueid, mothertongue;

    public String getMothertongueid() {
        return mothertongueid;
    }

    public void setMothertongueid(String mothertongueid) {
        this.mothertongueid = mothertongueid;
    }

    public String getMothertongue() {
        return mothertongue;
    }

    public void setMothertongue(String mothertongue) {
        this.mothertongue = mothertongue;
    }
}