package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.GotraModel;
import in.co.vsys.myssksamaj.model.responses.GotraResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author abhijeetjadhav
 */
public class GotraContract {

    public interface GotraAPI {
        @GET("matrimonyapp.asmx/SelectAllGotraMaster" )
        Call<GotraResponse> getAllGotra();
    }

    public interface GotraOps {
        void getAllGotra();

        void onDataReceived(GotraResponse gotraResponse);
    }

    public interface GotraView extends MVPView {
        void showAllGotra(List<GotraModel> gotraModels);
    }
}