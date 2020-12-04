package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.data_models.MyDataCountModel;
import in.co.vsys.myssksamaj.model.responses.GetDataCountResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class GetDataCountContract {
    public interface GetDataCountAPI {
        @GET("matrimonyapp.asmx/Member_SendRequest_ProfileVisited_ShortedListed_Blocked_Ignored_CountList")
        Call<GetDataCountResponse> getDataCountResponse(@Query("MemberId") String memberId);
    }

    public interface GetDataCountOps {
        void getDataCountResponse(String memberId);

        void onDataReceived(GetDataCountResponse getDataCountResponse);
    }

    public interface GetDataCountView extends MVPView {
        void showDataCount(MyDataCountModel myDataCountModel);
    }
}