package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 06/12/2017.
 */
public class WorkingInModel {

    private int workingAsId;
    private String workingAs;

    public WorkingInModel(int workingAsId, String workingAs) {
        this.workingAsId = workingAsId;
        this.workingAs = workingAs;
    }

    public WorkingInModel() {
    }

    public int getWorkingAsId() {
        return workingAsId;
    }

    public void setWorkingAsId(int workingAsId) {
        this.workingAsId = workingAsId;
    }

    public String getWorkingAs() {
        return workingAs;
    }

    public void setWorkingAs(String workingAs) {
        this.workingAs = workingAs;
    }

    @Override
    public String toString() {
        return getWorkingAs();
    }
}
