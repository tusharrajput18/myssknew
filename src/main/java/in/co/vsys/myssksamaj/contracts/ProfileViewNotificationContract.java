package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class ProfileViewNotificationContract {
    public interface ProfileViewNotificationAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/SendNotificationViewProfile")
        Call<Entity> sendViewProfileNotification(
                @Field("DeviceId") String DeviceId,
                @Field("Message") String Message,
                @Field("AppName") String AppName,
                @Field("ImageURL") String ImageURL,
                @Field("UserId") String UserId,
                @Field("MemberId") String MemberId,
                @Field("MemberName") String MemberName
        );
    }

    public interface ProfileViewNotificationOps {
        void sendViewProfileNotification(String DeviceId, String Message, String AppName, String ImageURL,
                                         String UserId, String MemberId, String MemberName);

        void onDataReceived(Entity entity);
    }

    public interface ProfileViewNotificationView extends MVPView {
        void showNotificationSent();
    }

}