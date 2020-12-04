package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.MyProfileViewedResponse;

import java.util.List;

import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class MyProfileViewedContract {

    public interface MyProfileViewedAPI {
        @GET("/matrimonyapp.asmx/Matrimony_Member_Who_Visited_MyProfile")
        Call<MyProfileViewedResponse> getPeopleWhoViewedMyProfile(@Query("MemberId") String memberId);
    }

    public interface MyProfileViewedOps {
        void getPeopleWhoViewedMyProfile(String memberId);

        void onDataReceived(MyProfileViewedResponse myProfileViewedResponse);
    }

    public interface MyProfileViewedView extends MVPView {
        void showPeopleWhoViewedMyProfile(List<UserProfileModel> userProfileModels);
    }

}