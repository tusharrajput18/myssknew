package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.RequestReceivedContract;
import in.co.vsys.myssksamaj.model.responses.RequestReceivedResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class RequestReceivedPresenter implements RequestReceivedContract.RequestReceivedOps {

    private RequestReceivedContract.RequestReceivedAPI requestReceivedAPI;
    private RequestReceivedContract.RequestReceivedView requestReceivedView;

    public RequestReceivedPresenter(RequestReceivedContract.RequestReceivedView requestReceivedView) {
        this.requestReceivedView = requestReceivedView;
        requestReceivedAPI = RestAdapterContainer.getInstance().create(RequestReceivedContract.RequestReceivedAPI.class);
    }

    @Override
    public void getRequestReceived(String MemberId) {
        requestReceivedView.showLoading();
        requestReceivedAPI.getRequestReceived(MemberId).enqueue(new Callback<RequestReceivedResponse>() {
            @Override
            public void onResponse(Call<RequestReceivedResponse> call, Response<RequestReceivedResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<RequestReceivedResponse> call, Throwable t) {
                requestReceivedView.hideLoading();
                requestReceivedView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(RequestReceivedResponse requestReceivedResponse) {
        requestReceivedView.hideLoading();
        if (requestReceivedResponse == null) {
            requestReceivedView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        requestReceivedView.showRequestReceived(requestReceivedResponse.getData());
    }
}