package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;

public class BlockListResponse extends Entity {

    private List<BlockListModelRes> data;

    public List<BlockListModelRes> getData() {
        return data;
    }

    public void setData(List<BlockListModelRes> data) {
        this.data = data;
    }
}
