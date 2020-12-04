package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.RecentlyProfileVisitedResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class RecentlyProfileViewedContract {
    public interface RecentlyProfileViewedAPI {
        @GET("/matrimonyapp.asmx/Matrimony_Member_RecentlyProfileVisitedList_paging")
//        @GET("/matrimonyapp.asmx/Matrimony_Member_RecentlyProfileVisitedList")
        Call<RecentlyProfileVisitedResponse> getRecentlyProfileVisited(
                @Query("MemberId") String MemberId,
                @Query("page") String page
        );
    }

    public interface RecentlyProfileViewedOps{
        void getRecentlyProfileVisited(String MemberId,String page);

        void onDataReceived(RecentlyProfileVisitedResponse recentlyProfileVisitedResponse);
    }

    public interface RecentlyProfileViewedView extends MVPView {
        void showRecentlyProfileViewed(List<UserProfileModel> data);
    }
}
