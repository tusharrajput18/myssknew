package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.RecentlyProfileViewedContract;
import in.co.vsys.myssksamaj.model.responses.RecentlyProfileVisitedResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class RecentlyProfileViewedPresenter implements RecentlyProfileViewedContract.RecentlyProfileViewedOps {

    private RecentlyProfileViewedContract.RecentlyProfileViewedAPI recentlyProfileViewedAPI;
    private RecentlyProfileViewedContract.RecentlyProfileViewedView recentlyProfileViewedView;

    public RecentlyProfileViewedPresenter(RecentlyProfileViewedContract.RecentlyProfileViewedView recentlyProfileViewedView) {
        this.recentlyProfileViewedView = recentlyProfileViewedView;
        recentlyProfileViewedAPI = RestAdapterContainer.getInstance().create(RecentlyProfileViewedContract.RecentlyProfileViewedAPI.class);
    }

    @Override
    public void getRecentlyProfileVisited(String MemberId, String page) {
        recentlyProfileViewedView.showLoading();
        recentlyProfileViewedAPI.getRecentlyProfileVisited(MemberId, page).enqueue(new Callback<RecentlyProfileVisitedResponse>() {
            @Override
            public void onResponse(Call<RecentlyProfileVisitedResponse> call, Response<RecentlyProfileVisitedResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<RecentlyProfileVisitedResponse> call, Throwable t) {
                recentlyProfileViewedView.hideLoading();
                recentlyProfileViewedView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(RecentlyProfileVisitedResponse recentlyProfileVisitedResponse) {
        recentlyProfileViewedView.hideLoading();
        if(recentlyProfileVisitedResponse == null || Utilities.isListEmpty(recentlyProfileVisitedResponse.getData())) {
            recentlyProfileViewedView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        recentlyProfileViewedView.showRecentlyProfileViewed(recentlyProfileVisitedResponse.getData());
    }
}
