package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.ImagesDataModel;
import in.co.vsys.myssksamaj.model.responses.ImagesDataResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class GetImagesContract {

    public interface GetImagesAPI {
        @GET("matrimonyapp.asmx/Matrimony_Member_AllPhotoList")
        Call<ImagesDataResponse> getAllPhotos(@Query("MemberId") String MemberId);
    }

    public interface GetImagesOps {
        void getAllPhotos(String MemberId);

        void onDataReceived(ImagesDataResponse imagesDataResponse);
    }

    public interface GetImagesView extends MVPView{
        void showImages(ImagesDataModel imagesDataModel);
    }
}