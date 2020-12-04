package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.RecentlyJointResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class RecentlyJointContract {
    public interface RecentlyJointAPI {
//        @GET("/matrimonyapp.asmx/Matrimony_NewlyJointUserList_paging")
        @GET("/matrimonyapp.asmx/Matrimony_NewlyJointUserList")
        Call<RecentlyJointResponse> getRecentlyJoint(
                @Query("MemberId") String MemberId
        );
    }

    public interface RecentlyJointOps{
        void getRecentlyJoint(String MemberId);

        void onDataReceived(RecentlyJointResponse recentlyJointResponse);
    }

    public interface RecentlyJointView extends MVPView {
        void showRecentlyJoint(List<UserProfileModel> data);
    }
}
