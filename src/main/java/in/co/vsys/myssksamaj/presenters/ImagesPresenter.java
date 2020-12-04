package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.ImagesContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.responses.ImagesDataResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class ImagesPresenter implements ImagesContract.ImageOps {

    private ImagesContract.LoadImageAPI loadImageAPI;
    private ImagesContract.ImagesView imagesView;
    private ImagesContract.UploadImagesView uploadImagesView;
    private ImagesContract.UploadImageAPI uploadImageAPI;

    public ImagesPresenter(ImagesContract.ImagesView imagesView) {
        this.imagesView = imagesView;
        loadImageAPI = RestAdapterContainer.getInstance().create(ImagesContract.LoadImageAPI.class);
    }

    public ImagesPresenter(ImagesContract.UploadImagesView uploadImagesView) {
        this.uploadImagesView = uploadImagesView;
        uploadImageAPI = RestAdapterContainer.getInstance().create(ImagesContract.UploadImageAPI.class);
    }

    @Override
    public void getAllImages(String memberId) {
        if (imagesView == null)
            return;

        imagesView.showLoading();
        loadImageAPI.getAllImages(memberId).enqueue(new Callback<ImagesDataResponse>() {
            @Override
            public void onResponse(Call<ImagesDataResponse> call, Response<ImagesDataResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<ImagesDataResponse> call, Throwable t) {
                imagesView.hideLoading();
                imagesView.showError("Could not upload image");
            }
        });
    }

    @Override
    public void uploadImages(String MEMBER_ID, String PROFILE_PHOTO, String PROFILE_EXTENSION, String mainphoto_showTo, String mainphoto_status,
                             String PHOTO_ONE, String PHOTO_ONE_EXTENSION, String photo_showTo1, String photo_status1,
                             String PHOTO_TWO, String PHOTO_TWO_EXTENSION, String photo_showTo2, String photo_status2,
                             String PHOTO_THREE, String PHOTO_THREE_EXTENSION, String photo_showTo3, String photo_status3,
                             String PHOTO_FOUR, String PHOTO_FOUR_EXTENSION, String photo_showTo4, String photo_status4,
                             String PHOTO_FIVE, String PHOTO_FIVE_EXTENSION, String photo_showTo5, String photo_status5,
                             String PHOTO_SIX, String PHOTO_SIX_EXTENSION, String photo_showTo6, String photo_status6,
                             String PHOTO_SEVEN, String PHOTO_SEVEN_EXTENSION, String photo_showTo7, String photo_status7,
                             String PHOTO_EIGHT, String PHOTO_EIGHT_EXTENSION, String photo_showTo8, String photo_status8,
                             String PHOTO_NINE, String PHOTO_NINE_EXTENSION, String photo_showTo9, String photo_status9,
                             String PHOTO_TEN, String PHOTO_TEN_EXTENSION, String photo_showTo10, String photo_status10,

                             int PHOTO_PROFILE_PERCENTAGE) {

        if (uploadImagesView == null)
            return;

        uploadImagesView.showLoading();
        uploadImageAPI.uploadImages(MEMBER_ID, PROFILE_PHOTO, PROFILE_EXTENSION, mainphoto_showTo, mainphoto_status,
                PHOTO_ONE, PHOTO_ONE_EXTENSION, photo_showTo1, photo_status1,
                PHOTO_TWO, PHOTO_TWO_EXTENSION, photo_showTo2, photo_status2,
                PHOTO_THREE, PHOTO_THREE_EXTENSION, photo_showTo3, photo_status3,
                PHOTO_FOUR, PHOTO_FOUR_EXTENSION, photo_showTo4, photo_status4,
                PHOTO_FIVE, PHOTO_FIVE_EXTENSION, photo_showTo5, photo_status5,
                PHOTO_SIX, PHOTO_SIX_EXTENSION, photo_showTo6, photo_status6,
                PHOTO_SEVEN, PHOTO_SEVEN_EXTENSION, photo_showTo7, photo_status7,
                PHOTO_EIGHT, PHOTO_EIGHT_EXTENSION, photo_showTo8, photo_status8,
                PHOTO_NINE, PHOTO_NINE_EXTENSION, photo_showTo9, photo_status9,
                PHOTO_TEN, PHOTO_TEN_EXTENSION, photo_showTo10, photo_status10,
                PHOTO_PROFILE_PERCENTAGE).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.message());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                uploadImagesView.hideLoading();
                uploadImagesView.showError("Could not upload image");
            }
        });
    }

    @Override
    public void onDataReceived(ImagesDataResponse imagesDataResponse) {
        try {
            if (imagesView == null)
                return;

            imagesView.hideLoading();
            if (imagesDataResponse == null || Utilities.isListEmpty(imagesDataResponse.getData())) {
                imagesView.showError("No images found");
                return;
            }
            imagesView.showImages(imagesDataResponse.getData().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceived(String uploaded) {

        if (uploadImagesView == null)
            return;

        uploadImagesView.hideLoading();
        if (Utilities.isEmpty(uploaded)) {
            uploadImagesView.showError("Could not upload image");
            uploadImagesView.showImageUploadFailed();
            return;
        }
        uploadImagesView.showImageUploaded();

    }
}
