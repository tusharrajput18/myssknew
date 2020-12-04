package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.InviteContract;
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
public class InvitePresenter implements InviteContract.InviteOps {

    private InviteContract.InviteAPI inviteAPI;
    private InviteContract.InviteView inviteView;

    public InvitePresenter(InviteContract.InviteView inviteView) {
        this.inviteView = inviteView;
        inviteAPI = RestAdapterContainer.getInstance().create(InviteContract.InviteAPI.class);
    }

    @Override
    public void performInvite(String SenderMemberid, String ReceiverMemberId, String Status) {
        inviteView.showLoading();
        inviteAPI.performInvite(SenderMemberid, ReceiverMemberId, Status).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                inviteView.hideLoading();
                inviteView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        inviteView.hideLoading();
        if (entity == null || Utilities.isEmpty(entity.getMessage())) {
            inviteView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        inviteView.showInvited(entity.getMessage());

    }
}
