package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.BlockContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class BlockPresenter implements BlockContract.BlockOps {

    private BlockContract.BlockAPI blockAPI;
    private BlockContract.BlockView blockView;

    public BlockPresenter(BlockContract.BlockView blockView) {
        this.blockView = blockView;
        blockAPI = RestAdapterContainer.getInstance().create(BlockContract.BlockAPI.class);
    }

    @Override
    public void updateBlockStatus(int senderId, int receiverId, int status) {
        blockView.showLoading();
        blockAPI.updateBlockStatus(senderId, receiverId, status).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                blockView.hideLoading();
                blockView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        blockView.showLoading();
        if(entity == null){
            blockView.hideLoading();
            blockView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        else {
            blockView.hideLoading();
            blockView.showBlockedStatus(entity.getMessage());
        }

    }
}
