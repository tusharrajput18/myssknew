package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetDistanceContract;
import in.co.vsys.myssksamaj.model.responses.GetDistanceResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDistancePresenter implements GetDistanceContract.GetDistanceOPS {

    private GetDistanceContract.GetDistanceView mGetDistanceView;
    private GetDistanceContract.GetDistanceAPI mGetDistanceAPI;

    public GetDistancePresenter(GetDistanceContract.GetDistanceView mGetDistanceView) {
        this.mGetDistanceView = mGetDistanceView;
        mGetDistanceAPI = RestAdapterContainer.getInstance().create(GetDistanceContract.GetDistanceAPI.class);
    }

    @Override
    public void getDistance() {

        mGetDistanceView.showLoading();
        mGetDistanceAPI.getDistance().enqueue(new Callback<GetDistanceResponse>() {
            @Override
            public void onResponse(Call<GetDistanceResponse> call, Response<GetDistanceResponse> response) {
                onDataRecieved(response.body());
            }

            @Override
            public void onFailure(Call<GetDistanceResponse> call, Throwable t) {
                mGetDistanceView.hideLoading();
                mGetDistanceView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDataRecieved(GetDistanceResponse response) {
        mGetDistanceView.hideLoading();
        mGetDistanceView.showDistanceList(response.getData());
    }
}
