package in.co.vsys.myssksamaj.model.data_models;

/**
 * Created by Vysys on 29/03/2018.
 */

public class BusinessTypeModel {

    private int businessId;
    private String businessName;
    private String businessImage;

    public BusinessTypeModel(int businessId, String businessName) {
        this.businessId = businessId;
        this.businessName = businessName;
    }

    public BusinessTypeModel(int businessId, String businessName, String businessImage) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessImage = businessImage;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }
}
