package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class InvitationNotificationContract {
    public interface InvitationNotificationAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/SendNotificationInvitation")
        Call<Entity> sendInvitationNotification(
                @Field("DeviceId") String DeviceId,
                @Field("Message") String Message,
                @Field("AppName") String AppName,
                @Field("ImageURL") String ImageURL,
                @Field("UserId") String UserId,
                @Field("MemberId") String MemberId,
                @Field("MemberName") String MemberName
        );
    }

    public interface InvitationNotificationOps {
        void sendInvitationNotification(String DeviceId, String Message, String AppName, String ImageURL,
                                        String UserId, String MemberId, String MemberName);

        void onDataReceived(Entity entity);
    }

    public interface InvitationNotificationView {
        void showInvitationNotificationSent();
    }
}