package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.ShortListContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class ShortListPresenter implements ShortListContract.ShortListOps {

    private ShortListContract.ShortListAPI shortListAPI;
    private ShortListContract.ShortListView shortListView;

    public ShortListPresenter(ShortListContract.ShortListView shortListView) {
        this.shortListView = shortListView;
        shortListAPI = RestAdapterContainer.getInstance().create(ShortListContract.ShortListAPI.class);
    }

    @Override
    public void performShortlist(String SenderMemberid, String ReceiverMemberId, String Status) {
        shortListView.showLoading();
        shortListAPI.performShortlist(SenderMemberid, ReceiverMemberId, Status).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                shortListView.hideLoading();
                shortListView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        shortListView.hideLoading();
        if (entity == null || Utilities.isEmpty(entity.getMessage())) {
            shortListView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        shortListView.showShortlisted(entity.getMessage());

    }
}
