package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 22-Jan-18.
 */

public class EduMadel {

    private int id;
    private String title;

    public EduMadel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
