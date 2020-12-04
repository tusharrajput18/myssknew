package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.RecentlyJointContractPaging;
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
public class RecentlyJointPagingPresenter implements RecentlyJointContractPaging.RecentlyJointPagingOps {

    private RecentlyJointContractPaging.RecentlyJointPagingAPI recentlyJointAPI;
    private RecentlyJointContractPaging.RecentlyJointPagingView recentlyJointView;

    public RecentlyJointPagingPresenter(RecentlyJointContractPaging.RecentlyJointPagingView recentlyJointView) {
        this.recentlyJointView = recentlyJointView;
        recentlyJointAPI = RestAdapterContainer.getInstance().create(RecentlyJointContractPaging.RecentlyJointPagingAPI.class);
    }

    @Override
    public void getRecentlyJoint(String MemberId, String page) {
        recentlyJointView.showLoading();
        recentlyJointAPI.getRecentlyJoint(MemberId, page).enqueue(new Callback<RecentlyJointResponse>() {
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
        if (recentlyJointResponse == null || Utilities.isListEmpty(recentlyJointResponse.getData())) {
            recentlyJointView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        recentlyJointView.showRecentlyJoint(recentlyJointResponse.getData());
    }
}
