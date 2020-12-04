package in.co.vsys.myssksamaj.contracts;

import android.content.Context;

import com.squareup.okhttp.ResponseBody;

import in.co.vsys.myssksamaj.model.RecentlyProfileVisitorModel;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author abhijeetjadhav
 */
public class DownloadProfileContract {

    public interface DownloadProfileAPI {
        @GET
        Call<ResponseBody> download(@Url String fileUrl);
    }

    public interface DownloadProfileOps {

        void  downloadFile(RecentlyProfileVisitorModel recentlyProfileVisitorModel,String fileUrl);

        void downloadFile(/*UserProfileModel userProfileModel,*/String fileName, String fileUrl);

        void downloadFile(UserDetailsModel userDetailsModel, String fileUrl);

        void onDataReceived();
    }

    public interface DownloadProfileView extends MVPView {
        void showFileDownloaded(String message);
    }
}