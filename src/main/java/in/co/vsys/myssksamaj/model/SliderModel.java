package in.co.vsys.myssksamaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderModel {

    @SerializedName("success")
    @Expose
    private int success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    List <SliderModelList> sliderModelLists;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SliderModelList> getSliderModelLists() {
        return sliderModelLists;
    }

    public void setSliderModelLists(List<SliderModelList> sliderModelLists) {
        this.sliderModelLists = sliderModelLists;
    }

    public class SliderModelList {

        @SerializedName("sid")
        @Expose
        String cid;

        @SerializedName("Image")
        @Expose
        private String Image;

        @SerializedName("ImageName")
        @Expose
        private String ImageName;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

        public String getImageName() {
            return ImageName;
        }

        public void setImageName(String imageName) {
            ImageName = imageName;
        }
    }
}
