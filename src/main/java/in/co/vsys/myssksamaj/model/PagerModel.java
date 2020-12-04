package in.co.vsys.myssksamaj.model;

/**
 * Created by Jack on 02/01/2018.
 */

public class PagerModel {

    String id;
    String imageUrl;

    public PagerModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PagerModel(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
