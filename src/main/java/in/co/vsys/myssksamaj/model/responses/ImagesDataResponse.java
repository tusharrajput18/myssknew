package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.ImagesDataModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class ImagesDataResponse extends Entity {
    private List<ImagesDataModel> data;

    public List<ImagesDataModel> getData() {
        return data;
    }

    public void setData(List<ImagesDataModel> data) {
        this.data = data;
    }
}