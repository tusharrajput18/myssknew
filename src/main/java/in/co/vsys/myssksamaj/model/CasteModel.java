package in.co.vsys.myssksamaj.model;

import com.google.gson.annotations.SerializedName;

public class CasteModel {

    @SerializedName("CasteId")
    private int casteId;
    @SerializedName("CasteName")
    private String casteName;

    public CasteModel() {
    }

    public int getCasteId() {
        return casteId;
    }

    public void setCasteId(int casteId) {
        this.casteId = casteId;
    }

    public String getCasteName() {
        return casteName;
    }

    public void setCasteName(String casteName) {
        this.casteName = casteName;
    }
}
