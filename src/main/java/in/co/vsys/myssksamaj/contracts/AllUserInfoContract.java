package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.responses.AllUserInfoResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class AllUserInfoContract {
    public interface AllUserInfoAPI {
        @GET("/matrimonyapp.asmx/Matrimony_UserAllDetailsInformation")
        Call<AllUserInfoResponse> getAllUserInfo(@Query("MemberId") String MemberId);
    }

    public interface AllUserInfoOps {
        void getAllUserInfoOps(String memberId);

        void onDataReceived(AllUserInfoResponse allUserInfoResponse);
    }

    public interface AllUserInfoView extends MVPView {
        void showAllUserInfo(UserDetailsModel userDetailsModel);
    }
}