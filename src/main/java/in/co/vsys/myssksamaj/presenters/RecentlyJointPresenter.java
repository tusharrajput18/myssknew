package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.RecentlyJointContract;
import in.co.vsys.myssksamaj.model.responses.RecentlyJointResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class RecentlyJointPresenter implements RecentlyJointContract.RecentlyJointOps {

    private RecentlyJointContract.RecentlyJointAPI recentlyJointAPI;
    private RecentlyJointContract.RecentlyJointView recentlyJointView;

    public RecentlyJointPresenter(RecentlyJointContract.RecentlyJointView recentlyJointView) {
        this.recentlyJointView = recentlyJointView;
        recentlyJointAPI = RestAdapterContainer.getInstance().create(RecentlyJointContract.RecentlyJointAPI.class);
    }

    @Override
    public void getRecentlyJoint(String MemberId) {
        recentlyJointView.showLoading();
        recentlyJointAPI.getRecentlyJoint(MemberId).enqueue(new Callback<RecentlyJointResponse>() {
            @Override
            public void onResponse(Call<RecentlyJointResponse> call, Response<RecentlyJointResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<RecentlyJointResponse> call, Throwable t) {
                recentlyJointView.hideLoading();
                recentlyJointView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(RecentlyJointResponse recentlyJointResponse) {
        recentlyJointView.hideLoading();
        if(recentlyJointResponse == null || Utilities.isListEmpty(recentlyJointResponse.getData())) {
            recentlyJointView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        recentlyJointView.showRecentlyJoint(recentlyJointResponse.getData());
    }
}
