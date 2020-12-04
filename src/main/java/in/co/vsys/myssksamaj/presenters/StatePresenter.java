package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.StateContract;
import in.co.vsys.myssksamaj.model.responses.StateResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class StatePresenter implements StateContract.StateOps {

    private StateContract.StateAPI stateAPI;
    private StateContract.StateView stateView;

    public StatePresenter(StateContract.StateView stateView) {
        this.stateView = stateView;
        stateAPI = RestAdapterContainer.getInstance().create(StateContract.StateAPI.class);
    }

    @Override
    public void getStateByCountry(String countryId) {
        stateView.showLoading();
        stateAPI.getStateByCountry(countryId).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                stateView.hideLoading();
                stateView.showError("No results found");
            }
        });
    }

    @Override
    public void onDataReceived(StateResponse stateResponse) {
        stateView.hideLoading();
        if(stateResponse == null){
            stateView.showError("No results found");
            return;
        }
        stateView.showStates(stateResponse.getData());
    }
}