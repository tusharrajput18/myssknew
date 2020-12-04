package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class SendNotificationContract {
    public interface SendNotificationAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/SendNotification")
        Call<Entity> sendNotification(
                @Field("DeviceId") String DeviceId,
                @Field("Message") String Message);
    }

    public interface SendNotificationOps {
        void sendNotification(String DeviceId, String Message);

        void onDataReceived(Entity entity);
    }

    public interface SendNotificationView{
        void showNotificationSent(String message);
    }
}