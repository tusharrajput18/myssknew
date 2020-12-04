package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.StateModel;
import in.co.vsys.myssksamaj.model.responses.StateResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class StateContract {
    public interface StateAPI {
        @GET("matrimonyapp.asmx/SelectAllStateUsingCountryId")
        Call<StateResponse> getStateByCountry(@Query("CountryID") String countryId);
    }

    public interface StateOps {
        void getStateByCountry(String countryId);

        void onDataReceived(StateResponse stateResponse);
    }

    public interface StateView extends MVPView{
        void showStates(List<StateModel> stateModels);
    }
}