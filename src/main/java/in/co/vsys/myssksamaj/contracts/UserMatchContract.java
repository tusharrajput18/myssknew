package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.UserMatchModel;
import in.co.vsys.myssksamaj.model.responses.UserMatchResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class UserMatchContract {
    public interface UserMatchAPI {
                @GET("/matrimonyapp.asmx/Matrimony_Member_MatchList_paging")
//        @GET("/matrimonyapp.asmx/Matrimony_Member_MatchList")
        Call<UserMatchResponse> getUserMatch(
                @Query("MemberId") String MemberId,
                @Query("page") String page
        );
    }

    public interface UserMatchOps {
        void getUserMatch(String MemberId, String page);

        void onDataReceived(UserMatchResponse userMatchResponse);
    }

    public interface UserMatchView extends MVPView {
        void showUserMatch(List<UserMatchModel> data);
    }
}