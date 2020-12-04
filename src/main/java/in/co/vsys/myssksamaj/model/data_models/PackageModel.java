package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

/**
 * @author abhijeetjadhav
 */
public class PackageModel implements Serializable {
    private String packageid, packagename, packagedescription, contact, amt, isdeleted;

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getPackagedescription() {
        return packagedescription;
    }

    public void setPackagedescription(String packagedescription) {
        this.packagedescription = packagedescription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }
}