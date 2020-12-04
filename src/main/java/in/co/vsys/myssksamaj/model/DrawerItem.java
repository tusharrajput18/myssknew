package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 28/11/2017.
 */

public class DrawerItem {

    private String marriedStatus;
    private int motherTongueId;
    private String motherTongueName;
    private String height;
    private boolean isSelected = false;

    public DrawerItem() {
    }

    public DrawerItem(int motherTongueId, String motherTongueName) {
        this.motherTongueId = motherTongueId;
        this.motherTongueName = motherTongueName;
    }

    public DrawerItem(int motherTongueId, String motherTongueName, String height) {
        this.motherTongueId = motherTongueId;
        this.motherTongueName = motherTongueName;
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public DrawerItem(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    public String getMarriedStatus() {
        return marriedStatus;
    }

    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    public int getMotherTongueId() {
        return motherTongueId;
    }

    public void setMotherTongueId(int motherTongueId) {
        this.motherTongueId = motherTongueId;
    }

    public String getMotherTongueName() {
        return motherTongueName;
    }

    public void setMotherTongueName(String motherTongueName) {
        this.motherTongueName = motherTongueName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return marriedStatus;
    }
}
