package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.CasteModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class CasteResponse extends Entity {
    private List<CasteModel> data;

    public List<CasteModel> getData() {
        return data;
    }
}