package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.DistanceModel;
import in.co.vsys.myssksamaj.model.responses.GetDistanceResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;

public class GetDistanceContract {

    public interface GetDistanceAPI {
        @GET("matrimonyapp.asmx/getDistances")
        Call<GetDistanceResponse> getDistance();
    }

    public interface GetDistanceOPS {
        void getDistance();

        void onDataRecieved(GetDistanceResponse response);
    }

    public interface GetDistanceView extends MVPView {
        void showDistanceList(List<DistanceModel> distanceModels);
    }
}
