package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 06/12/2017.
 */
public class WorkingWithModel {

    private int workingWithId;
    private String workingWith;

    public WorkingWithModel() {
    }

    public WorkingWithModel(int workingWithId, String workingWith) {
        this.workingWithId = workingWithId;
        this.workingWith = workingWith;
    }

    public int getWorkingWithId() {
        return workingWithId;
    }

    public void setWorkingWithId(int workingWithId) {
        this.workingWithId = workingWithId;
    }

    public String getWorkingWith() {
        return workingWith;
    }

    public void setWorkingWith(String workingWith) {
        this.workingWith = workingWith;
    }

    @Override
    public String toString() {
        return getWorkingWith();
    }
}

