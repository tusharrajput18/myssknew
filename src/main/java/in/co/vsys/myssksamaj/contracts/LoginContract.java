package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.UserLoginModel;
import in.co.vsys.myssksamaj.model.responses.CandidateLoginResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class LoginContract {
    public interface LoginAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/Matrimony_UserLogin")
        Call<CandidateLoginResponse> loginUser(
                @Field("mobile") String mobile,
                @Field("password") String password,
                @Field("deviceid") String deviceid);
    }

    public interface LoginOps {
        void loginUser(String mobile, String password, String deviceid);

        void onDataReceived(CandidateLoginResponse candidateLoginResponse);
    }

    public interface LoginView extends MVPView {
        void showCandidateLogin(UserLoginModel userLoginModel);
    }
}