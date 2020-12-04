package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.AcceptInvitationContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class AcceptInvitationPresenter implements AcceptInvitationContract.AcceptInvitationOps {

    private AcceptInvitationContract.AcceptInvitationAPI acceptInvitationAPI;
    private AcceptInvitationContract.AcceptInvitationView acceptInvitationView;

    public AcceptInvitationPresenter(AcceptInvitationContract.AcceptInvitationView acceptInvitationView) {
        this.acceptInvitationView = acceptInvitationView;
        acceptInvitationAPI = RestAdapterContainer.getInstance().create(AcceptInvitationContract.AcceptInvitationAPI.class);
    }

    @Override
    public void acceptInvitation(String SenderMemberid, String ReceiverMemberId, String Status) {
        acceptInvitationView.showLoading();
        acceptInvitationAPI.acceptInvitation(SenderMemberid, ReceiverMemberId, Status).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                acceptInvitationView.hideLoading();
                acceptInvitationView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        acceptInvitationView.hideLoading();
        if (entity == null) {
            acceptInvitationView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        acceptInvitationView.showInvitationAccepted(entity.getMessage());
    }
}
