package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.LoginContract;
import in.co.vsys.myssksamaj.model.responses.CandidateLoginResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class LoginPresenter implements LoginContract.LoginOps {

    private LoginContract.LoginAPI loginAPI;
    private LoginContract.LoginView loginView;

    public LoginPresenter(LoginContract.LoginView loginView) {
        this.loginView = loginView;
        loginAPI = RestAdapterContainer.getInstance().create(LoginContract.LoginAPI.class);
    }

    @Override
    public void loginUser(String mobile, String password, String deviceid) {
        loginView.showLoading();
        loginAPI.loginUser(mobile, password, deviceid).enqueue(new Callback<CandidateLoginResponse>() {
            @Override
            public void onResponse(Call<CandidateLoginResponse> call, Response<CandidateLoginResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<CandidateLoginResponse> call, Throwable t) {
                loginView.hideLoading();
                loginView.showError(Constants.ERROR_HANDLING.SERVER_ERROR);
            }
        });
    }

    @Override
    public void onDataReceived(CandidateLoginResponse candidateLoginResponse) {
        loginView.hideLoading();

        if(candidateLoginResponse == null || Utilities.isListEmpty(candidateLoginResponse.getData())){
            loginView.showError(candidateLoginResponse.getMessage());
            return;
        }
        loginView.showCandidateLogin(candidateLoginResponse.getData().get(0));
    }
}