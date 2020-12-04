package in.co.vsys.myssksamaj.model.responses;

import in.co.vsys.myssksamaj.model.data_models.MemberDetailsModel;

import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class MemberDetailsResponse extends Entity {
    private List<MemberDetailsModel> data;

    public List<MemberDetailsModel> getData() {
        return data;
    }

    public void setData(List<MemberDetailsModel> data) {
        this.data = data;
    }
}