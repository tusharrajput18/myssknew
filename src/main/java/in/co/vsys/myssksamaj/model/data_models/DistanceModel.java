package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

public class DistanceModel implements Serializable {

    private String id;
    private String distance;
    private String isactive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
