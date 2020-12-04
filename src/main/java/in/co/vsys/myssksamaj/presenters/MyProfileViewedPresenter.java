package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.model.responses.MyProfileViewedResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class MyProfileViewedPresenter implements MyProfileViewedContract.MyProfileViewedOps {

    private MyProfileViewedContract.MyProfileViewedAPI myProfileViewedAPI;
    private MyProfileViewedContract.MyProfileViewedView myProfileViewedView;

    public MyProfileViewedPresenter(MyProfileViewedContract.MyProfileViewedView myProfileViewedView) {
        this.myProfileViewedView = myProfileViewedView;
        myProfileViewedAPI = RestAdapterContainer.getInstance().create(MyProfileViewedContract.MyProfileViewedAPI.class);
    }

    @Override
    public void getPeopleWhoViewedMyProfile(String memberId) {
        myProfileViewedView.showLoading();
        myProfileViewedAPI.getPeopleWhoViewedMyProfile(memberId).enqueue(new Callback<MyProfileViewedResponse>() {
            @Override
            public void onResponse(Call<MyProfileViewedResponse> call, Response<MyProfileViewedResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<MyProfileViewedResponse> call, Throwable t) {
                myProfileViewedView.hideLoading();
                myProfileViewedView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(MyProfileViewedResponse myProfileViewedResponse) {
        if (myProfileViewedResponse == null) {
            return;
        }
        myProfileViewedView.showPeopleWhoViewedMyProfile(myProfileViewedResponse.getData());
    }
}
