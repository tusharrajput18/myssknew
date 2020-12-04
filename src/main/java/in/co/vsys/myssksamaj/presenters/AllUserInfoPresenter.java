package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.AllUserInfoContract;
import in.co.vsys.myssksamaj.model.responses.AllUserInfoResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class AllUserInfoPresenter implements AllUserInfoContract.AllUserInfoOps {

    private AllUserInfoContract.AllUserInfoAPI allUserInfoAPI;
    private AllUserInfoContract.AllUserInfoView allUserInfoView;

    public AllUserInfoPresenter(AllUserInfoContract.AllUserInfoView allUserInfoView) {
        this.allUserInfoView = allUserInfoView;
        allUserInfoAPI = RestAdapterContainer.getInstance().create(AllUserInfoContract.AllUserInfoAPI.class);
    }

    @Override
    public void getAllUserInfoOps(String memberId) {
        allUserInfoView.showLoading();
        allUserInfoAPI.getAllUserInfo(memberId).enqueue(new Callback<AllUserInfoResponse>() {
            @Override
            public void onResponse(Call<AllUserInfoResponse> call, Response<AllUserInfoResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<AllUserInfoResponse> call, Throwable t) {
                allUserInfoView.hideLoading();
                allUserInfoView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(AllUserInfoResponse allUserInfoResponse) {
        allUserInfoView.hideLoading();
        if(allUserInfoResponse == null || Utilities.isListEmpty(allUserInfoResponse.getData())) {
            allUserInfoView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        allUserInfoView.showAllUserInfo(allUserInfoResponse.getData().get(0));
    }
}
