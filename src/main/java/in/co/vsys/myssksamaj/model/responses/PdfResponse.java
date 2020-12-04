package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.PdfModel;

/**
 * @author abhijeetjadhav
 */
public class PdfResponse extends Entity {
    private List<PdfModel> data;

    public List<PdfModel> getData() {
        return data;
    }
}