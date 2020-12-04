package in.co.vsys.myssksamaj.presenters;

import android.support.v4.app.FragmentActivity;

import in.co.vsys.myssksamaj.contracts.BlockListContract;
import in.co.vsys.myssksamaj.contracts.GetVisitorsByRoleContract;
import in.co.vsys.myssksamaj.fragments.BlockProfile;
import in.co.vsys.myssksamaj.model.responses.BlockListResponse;
import in.co.vsys.myssksamaj.model.responses.VisitorInfoResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class BlockListPresenter implements BlockListContract.BlockListOps {

    private BlockListContract.BlockListAPI getblockListApi;
    private BlockListContract.GetBlockListView getBlockListView;

    public BlockListPresenter(BlockListContract.GetBlockListView blockProfile) {
        this.getBlockListView=blockProfile;
        getblockListApi = RestAdapterContainer.getInstance().create(BlockListContract.BlockListAPI.class);
    }


    @Override
    public void getBlockListByMemberId(String MemberId) {
        getBlockListView.showLoading();
        getblockListApi.getBlockList(MemberId).enqueue(new Callback<BlockListResponse>() {
            @Override
            public void onResponse(Call<BlockListResponse> call, Response<BlockListResponse> response) {

                onDataReceived(response.body());

            }

            @Override
            public void onFailure(Call<BlockListResponse> call, Throwable t) {
                getBlockListView.hideLoading();
                getBlockListView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);

            }
        });
    }

    @Override
    public void onDataReceived(BlockListResponse blockListResponse) {
        getBlockListView.hideLoading();
        if(blockListResponse==null || Utilities.isListEmpty(blockListResponse.getData())){
            getBlockListView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        getBlockListView.showUsers(blockListResponse.getData());

    }
}
