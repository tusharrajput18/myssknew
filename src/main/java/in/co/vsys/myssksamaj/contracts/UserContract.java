package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.responses.UserDetailsResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class UserContract {

    public interface UserDetailsAPI {
        @GET("matrimonyapp.asmx/Matrimony_UserAllDetailsInformation")
        Call<UserDetailsResponse> getUserDetails(@Query("MemberId") String memberId);
    }

    public interface UserDetailsOps {
        void getUserDetails(String memberId);

        void onDataReceived(UserDetailsResponse userDetailsResponse);
    }

    public interface UserDetailsView extends MVPView {
        void showUserDetails(UserDetailsModel userDetailsModel);
    }
}