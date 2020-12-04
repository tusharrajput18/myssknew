package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.CountryContract;
import in.co.vsys.myssksamaj.model.responses.CountryResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class CountryPresenter implements CountryContract.CountryOps {

    private CountryContract.CountryAPI countryAPI;
    private CountryContract.CountryView countryView;

    public CountryPresenter(CountryContract.CountryView countryView) {
        this.countryView = countryView;
        countryAPI = RestAdapterContainer.getInstance().create(CountryContract.CountryAPI.class);
    }

    @Override
    public void getCountries() {
        countryView.showLoading();
        countryAPI.getCountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                countryView.hideLoading();
                countryView.showError("No results found");
            }
        });
    }

    @Override
    public void onDataReceived(CountryResponse countryResponse) {
        countryView.hideLoading();
        if (countryResponse == null) {
            countryView.showError("No results found");
            return;
        }
        countryView.showCountries(countryResponse.getData());
    }
}
