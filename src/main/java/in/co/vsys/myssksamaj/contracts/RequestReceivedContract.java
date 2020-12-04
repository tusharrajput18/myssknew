package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.RequestReceivedResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class RequestReceivedContract {
    public interface RequestReceivedAPI {
        @GET("/matrimonyapp.asmx/Matrimony_Member_Who_SendRequest_You_List")
        Call<RequestReceivedResponse> getRequestReceived(@Query("MemberId") String MemberId);
    }

    public interface RequestReceivedOps {
        void getRequestReceived(String MemberId);

        void onDataReceived(RequestReceivedResponse requestReceivedResponse);
    }

    public interface RequestReceivedView extends MVPView {
        void showRequestReceived(List<UserProfileModel> data);
    }
}