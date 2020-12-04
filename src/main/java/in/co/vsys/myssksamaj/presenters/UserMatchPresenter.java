package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.UserMatchContract;
import in.co.vsys.myssksamaj.model.responses.UserMatchResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class UserMatchPresenter implements UserMatchContract.UserMatchOps {

    private UserMatchContract.UserMatchAPI userMatchAPI;
    private UserMatchContract.UserMatchView userMatchView;

    public UserMatchPresenter(UserMatchContract.UserMatchView userMatchView) {
        this.userMatchView = userMatchView;
        userMatchAPI = RestAdapterContainer.getInstance().create(UserMatchContract.UserMatchAPI.class);
    }

    @Override
    public void getUserMatch(String MemberId, String page) {
        userMatchView.showLoading();
        userMatchAPI.getUserMatch(MemberId, page).enqueue(new Callback<UserMatchResponse>() {
            @Override
            public void onResponse(Call<UserMatchResponse> call, Response<UserMatchResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<UserMatchResponse> call, Throwable t) {
                userMatchView.hideLoading();
                userMatchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(UserMatchResponse userMatchResponse) {
        userMatchView.hideLoading();
        if (userMatchResponse == null || Utilities.isListEmpty(userMatchResponse.getData())) {
            userMatchView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        userMatchView.showUserMatch(userMatchResponse.getData());
    }
}
