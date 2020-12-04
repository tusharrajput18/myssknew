package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.GetInvitedListResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class GetInvitedListContract {

    public interface GetInvitedListAPI {
        @GET("matrimonyapp.asmx/Matrimony_Member_SendRequestList")
        Call<GetInvitedListResponse> getInvitedList(@Query("MemberId") String MemberId);
    }

    public interface GetInvitedListOps{
        void getInvitedList(String MemberId);

        void onDataReceived(GetInvitedListResponse getInvitedListResponse);
    }

    public interface GetInvitedListView extends MVPView{
        void showInvitedList(List<UserProfileModel> data);
    }
}