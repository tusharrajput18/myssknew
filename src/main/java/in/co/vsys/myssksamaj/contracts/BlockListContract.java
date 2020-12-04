package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.fragments.BlockProfile;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.BlockListModelRes;
import in.co.vsys.myssksamaj.model.responses.BlockListResponse;
import in.co.vsys.myssksamaj.model.responses.VisitorInfoResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class BlockListContract {

    public interface BlockListAPI {
        @GET("/matrimonyapp.asmx/getBlockedMembers")
        Call<BlockListResponse> getBlockList(@Query("MemberId") String MemberId);
    }

    public interface BlockListOps {
        void getBlockListByMemberId(String MemberId);

        void onDataReceived(BlockListResponse blockListResponse);
    }

    public interface GetBlockListView extends MVPView {

        void showUsers(List<BlockListModelRes> userProfileModels);
    }
}