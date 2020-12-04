package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

/**
 * @author abhijeetjadhav
 */
public class SubcasteModel implements Serializable {
    private String SubCatseId, SubCasteName;

    public String getSubCatseId() {
        return SubCatseId;
    }

    public void setSubCatseId(String subCatseId) {
        SubCatseId = subCatseId;
    }

    public String getSubCasteName() {
        return SubCasteName;
    }

    public void setSubCasteName(String subCasteName) {
        SubCasteName = subCasteName;
    }
}