package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.MemberDetailsModel;
import in.co.vsys.myssksamaj.model.responses.MemberDetailsResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class MemberDetailsContract {
    public interface MemberDetailsAPI {
        @GET("matrimonyapp.asmx/Matrimony_UserAllDetailsInformation_Using_UniqueId")
        Call<MemberDetailsResponse> getMemberDetails(@Query("UniqueId") String UniqueId);
    }

    public interface MemberDetailsOps {
        void getMemberDetails(String UniqueId);

        void onDataReceived(MemberDetailsResponse memberDetailsResponse);
    }

    public interface MemberDetailsView extends MVPView {
        void showMemberDetails(List<MemberDetailsModel> memberDetailsData);
    }
}