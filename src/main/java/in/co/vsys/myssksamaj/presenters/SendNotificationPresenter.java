package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.SendNotificationContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class SendNotificationPresenter implements SendNotificationContract.SendNotificationOps {

    private SendNotificationContract.SendNotificationAPI sendNotificationAPI;
    private SendNotificationContract.SendNotificationView sendNotificationView;

    public SendNotificationPresenter(SendNotificationContract.SendNotificationView sendNotificationView) {
        this.sendNotificationView = sendNotificationView;
        sendNotificationAPI = RestAdapterContainer.getInstance().create(SendNotificationContract.SendNotificationAPI.class);
    }

    @Override
    public void sendNotification(String DeviceId, String Message) {
        sendNotificationAPI.sendNotification(DeviceId, Message).enqueue(new Callback<Entity>() {
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
        sendNotificationView.showNotificationSent(entity.getMessage());
    }
}
