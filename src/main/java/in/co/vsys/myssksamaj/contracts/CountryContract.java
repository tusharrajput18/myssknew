package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.responses.CountryResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author abhijeetjadhav
 */
public class CountryContract {

    public interface CountryAPI {
        @GET("matrimonyapp.asmx/SelectAllCountry" )
        Call<CountryResponse> getCountries();
    }

    public interface CountryOps {
        void getCountries();

        void onDataReceived(CountryResponse countryResponse);
    }

    public interface CountryView extends MVPView {
        void showCountries(List<CountryModel> countryModels);
    }
}