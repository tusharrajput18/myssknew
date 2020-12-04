package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;
import java.io.StringReader;

public class AllAnnualIncomeModel implements Serializable {

    private String annualincomeid;
    private String annualincome;

    public String getAnnualincomeid() {
        return annualincomeid;
    }

    public void setAnnualincomeid(String annualincomeid) {
        this.annualincomeid = annualincomeid;
    }

    public String getAnnualincome() {
        return annualincome;
    }

    public void setAnnualincome(String annualincome) {
        this.annualincome = annualincome;
    }
}
