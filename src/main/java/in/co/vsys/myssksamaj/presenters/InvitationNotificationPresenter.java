package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.InvitationNotificationContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class InvitationNotificationPresenter implements InvitationNotificationContract.InvitationNotificationOps {

    private InvitationNotificationContract.InvitationNotificationAPI invitationNotificationAPI;
    private InvitationNotificationContract.InvitationNotificationView invitationNotificationView;

    public InvitationNotificationPresenter(InvitationNotificationContract.InvitationNotificationView invitationNotificationView) {
        this.invitationNotificationView = invitationNotificationView;
        invitationNotificationAPI = RestAdapterContainer.getInstance().create(InvitationNotificationContract.InvitationNotificationAPI.class);
    }

    @Override
    public void sendInvitationNotification(String DeviceId, String Message, String AppName, String ImageURL,
                                           String UserId, String MemberId, String MemberName) {
        invitationNotificationAPI.sendInvitationNotification(DeviceId, Message, AppName, ImageURL, UserId,
                MemberId, MemberName).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
    }
}