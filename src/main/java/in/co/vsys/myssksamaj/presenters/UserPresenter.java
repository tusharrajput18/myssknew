package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.UserContract;
import in.co.vsys.myssksamaj.model.responses.UserDetailsResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class UserPresenter implements UserContract.UserDetailsOps {

    private UserContract.UserDetailsAPI userDetailsAPI;
    private UserContract.UserDetailsView userDetailsView;

    public UserPresenter(UserContract.UserDetailsView userDetailsView) {
        this.userDetailsView = userDetailsView;
        userDetailsAPI = RestAdapterContainer.getInstance().create(UserContract.UserDetailsAPI.class);
    }

    @Override
    public void getUserDetails(String memberId) {
        userDetailsView.showLoading();
        userDetailsAPI.getUserDetails(memberId).enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                userDetailsView.hideLoading();
                //userDetailsView.showError("Could not get details.");
            }
        });
    }

    @Override
    public void onDataReceived(UserDetailsResponse userDetailsResponse) {
        userDetailsView.hideLoading();
        if(userDetailsResponse == null || Utilities.isListEmpty(userDetailsResponse.getData())) {
            userDetailsView.showError("Could not get details1.");
            return;
        }
        userDetailsView.showUserDetails(userDetailsResponse.getData().get(0));
    }
}
