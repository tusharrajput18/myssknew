package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.RecentlyJointResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class RecentlyJointContractPaging {
    public interface RecentlyJointPagingAPI {
        @GET("/matrimonyapp.asmx/Matrimony_NewlyJointUserList_paging")
//        @GET("/matrimonyapp.asmx/Matrimony_NewlyJointUserList")
        Call<RecentlyJointResponse> getRecentlyJoint(
                @Query("MemberId") String MemberId,
                @Query("page") String page
        );
    }

    public interface RecentlyJointPagingOps{
        void getRecentlyJoint(String MemberId, String page);

        void onDataReceived(RecentlyJointResponse recentlyJointResponse);
    }

    public interface RecentlyJointPagingView extends MVPView {
        void showRecentlyJoint(List<UserProfileModel> data);
    }
}
