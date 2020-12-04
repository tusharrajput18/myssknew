package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetImagesContract;
import in.co.vsys.myssksamaj.model.responses.ImagesDataResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class GetImagesPresenter implements GetImagesContract.GetImagesOps {

    private GetImagesContract.GetImagesAPI getImagesAPI;
    private GetImagesContract.GetImagesView getImagesView;

    public GetImagesPresenter(GetImagesContract.GetImagesView getImagesView) {
        this.getImagesView = getImagesView;
        getImagesAPI = RestAdapterContainer.getInstance().create(GetImagesContract.GetImagesAPI.class);
    }

    @Override
    public void getAllPhotos(String MemberId) {
        getImagesView.showLoading();
        getImagesAPI.getAllPhotos(MemberId).enqueue(new Callback<ImagesDataResponse>() {
            @Override
            public void onResponse(Call<ImagesDataResponse> call, Response<ImagesDataResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<ImagesDataResponse> call, Throwable t) {
                getImagesView.hideLoading();
                getImagesView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(ImagesDataResponse imagesDataResponse) {
        try {
            getImagesView.hideLoading();
            if (imagesDataResponse == null || Utilities.isListEmpty(imagesDataResponse.getData())) {
                getImagesView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
                return;
            }
            getImagesView.showImages(imagesDataResponse.getData().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
