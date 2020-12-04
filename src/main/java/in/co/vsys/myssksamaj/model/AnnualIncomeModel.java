package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 06/12/2017.
 */

public class AnnualIncomeModel {

    private int annualIncomeId;
    private String annualIncome;

    public AnnualIncomeModel(int annualIncomeId, String annualIncome) {
        this.annualIncomeId = annualIncomeId;
        this.annualIncome = annualIncome;
    }

    public AnnualIncomeModel() {
    }

    public int getAnnualIncomeId() {
        return annualIncomeId;
    }

    public void setAnnualIncomeId(int annualIncomeId) {
        this.annualIncomeId = annualIncomeId;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    @Override
    public String toString() {
        return getAnnualIncome();
    }
}
