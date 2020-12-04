package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.CityContract;
import in.co.vsys.myssksamaj.model.responses.CityResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class CityPresenter implements CityContract.CityOps {
    private CityContract.CityAPI cityAPI;
    private CityContract.CityView cityView;

    public CityPresenter(CityContract.CityView cityView) {
        this.cityView = cityView;
        cityAPI = RestAdapterContainer.getInstance().create(CityContract.CityAPI.class);
    }

    @Override
    public void getCityByState(String stateId) {
        cityView.showLoading();
        cityAPI.getCityByState(stateId).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                cityView.hideLoading();
                cityView.showError("No results found");
            }
        });
    }

    @Override
    public void onDataReceived(CityResponse cityResponse) {
        cityView.hideLoading();
        if (cityResponse == null) {
            cityView.showError("No results found");
            return;
        }
        cityView.showCities(cityResponse.getData());
    }
}