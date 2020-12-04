package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetInvitedListContract;
import in.co.vsys.myssksamaj.model.responses.GetInvitedListResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class GetInvitedListPresenter implements GetInvitedListContract.GetInvitedListOps {

    private GetInvitedListContract.GetInvitedListAPI getInvitedListAPI;
    private GetInvitedListContract.GetInvitedListView getInvitedListView;

    public GetInvitedListPresenter(GetInvitedListContract.GetInvitedListView getInvitedListView) {
        this.getInvitedListView = getInvitedListView;
        getInvitedListAPI = RestAdapterContainer.getInstance().create(GetInvitedListContract.GetInvitedListAPI.class);
    }

    @Override
    public void getInvitedList(String MemberId) {
        getInvitedListView.showLoading();
        getInvitedListAPI.getInvitedList(MemberId).enqueue(new Callback<GetInvitedListResponse>() {
            @Override
            public void onResponse(Call<GetInvitedListResponse> call, Response<GetInvitedListResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<GetInvitedListResponse> call, Throwable t) {
                getInvitedListView.hideLoading();
                getInvitedListView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(GetInvitedListResponse getInvitedListResponse) {
        getInvitedListView.hideLoading();
        if (getInvitedListResponse == null || Utilities.isListEmpty(getInvitedListResponse.getData())) {
            getInvitedListView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        getInvitedListView.showInvitedList(getInvitedListResponse.getData());
    }
}
