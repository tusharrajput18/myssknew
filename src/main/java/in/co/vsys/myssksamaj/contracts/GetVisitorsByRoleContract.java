package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.VisitorInfoResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class GetVisitorsByRoleContract {

    public interface GetVisitorsAPI {
        @GET("/matrimonyapp.asmx/getVisitorsByRole")
        Call<VisitorInfoResponse> getVisitorsByRole(@Query("MemberId") String MemberId
                , @Query("role") String role);
    }

    public interface GetVisitorsOps {
        void getVisitorsByRole(String MemberId, String role);

        void onDataReceived(VisitorInfoResponse visitorInfoResponse);
    }

    public interface GetVisitorView extends MVPView {
        void showUsers(List<UserProfileModel> userProfileModels);
    }
}