package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetDataCountContract;
import in.co.vsys.myssksamaj.model.responses.GetDataCountResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class GetDataCountPresenter implements GetDataCountContract.GetDataCountOps {

    private GetDataCountContract.GetDataCountAPI getDataCountAPI;
    private GetDataCountContract.GetDataCountView getDataCountView;

    public GetDataCountPresenter(GetDataCountContract.GetDataCountView getDataCountView) {
        this.getDataCountView = getDataCountView;
        getDataCountAPI = RestAdapterContainer.getInstance().create(GetDataCountContract.GetDataCountAPI.class);
    }

    @Override
    public void getDataCountResponse(String memberId) {
        getDataCountView.showLoading();
        getDataCountAPI.getDataCountResponse(memberId).enqueue(new Callback<GetDataCountResponse>() {
            @Override
            public void onResponse(Call<GetDataCountResponse> call, Response<GetDataCountResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<GetDataCountResponse> call, Throwable t) {
                getDataCountView.hideLoading();
                getDataCountView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(GetDataCountResponse getDataCountResponse) {
        getDataCountView.hideLoading();
        if (getDataCountResponse == null || Utilities.isListEmpty(getDataCountResponse.getData())) {
            getDataCountView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        getDataCountView.showDataCount(getDataCountResponse.getData().get(0));
    }
}