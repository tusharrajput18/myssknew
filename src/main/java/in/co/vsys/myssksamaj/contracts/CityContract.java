package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.CityModel;
import in.co.vsys.myssksamaj.model.responses.CityResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class CityContract {
    public interface CityAPI {
        @GET("matrimonyapp.asmx/SelectAllCityMasterUsingStateId")
        Call<CityResponse> getCityByState(@Query("StateId") String stateId);
    }

    public interface CityOps {
        void getCityByState(String stateId);

        void onDataReceived(CityResponse cityResponse);
    }

    public interface CityView extends MVPView {
        void showCities(List<CityModel> cityModels);
    }
}