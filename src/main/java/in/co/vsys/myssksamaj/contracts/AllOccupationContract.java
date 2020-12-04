package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.OccupationModel;
import in.co.vsys.myssksamaj.model.responses.AllOccupationResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;

public class AllOccupationContract {

    public interface AllOccupationAPI {
        @GET("/matrimonyapp.asmx/SelectAllOccupation")
        Call<AllOccupationResponse> getAlloccuption();
    }

    public interface AllOccupationOPS {
        void getAlloccuption();

        void onDataRecieved(AllOccupationResponse response);
    }

    public interface AllOccupationView extends MVPView {
        void showAllOccuption(List<OccupationModel> occupationList);
    }

}
