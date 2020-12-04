package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.AllOccupationContract;
import in.co.vsys.myssksamaj.model.responses.AllOccupationResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOccupationPresenter implements AllOccupationContract.AllOccupationOPS {

    AllOccupationContract.AllOccupationView mAllOccupationView;
    AllOccupationContract.AllOccupationAPI mAllOccupationAPI;

    public AllOccupationPresenter(AllOccupationContract.AllOccupationView mAllOccupationView) {
        this.mAllOccupationView = mAllOccupationView;
        mAllOccupationAPI = RestAdapterContainer.getInstance().create(AllOccupationContract.AllOccupationAPI.class);
    }

    @Override
    public void getAlloccuption() {
        mAllOccupationView.showLoading();
        mAllOccupationAPI.getAlloccuption().enqueue(new Callback<AllOccupationResponse>() {
            @Override
            public void onResponse(Call<AllOccupationResponse> call, Response<AllOccupationResponse> response) {
                onDataRecieved(response.body());
            }
            @Override
            public void onFailure(Call<AllOccupationResponse> call, Throwable t) {
                mAllOccupationView.hideLoading();
                mAllOccupationView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDataRecieved(AllOccupationResponse response) {
        if(response==null){

        }
            mAllOccupationView.hideLoading();
            mAllOccupationView.showAllOccuption(response.getData());
    }
}
