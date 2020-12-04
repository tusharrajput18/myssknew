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
public class AcceptInvitationContract {
    public interface AcceptInvitationAPI {
        @FormUrlEncoded
        @POST("matrimonyapp.asmx/Member_AcceptRequestUpdateStatus")
        Call<Entity> acceptInvitation(
                @Field("SenderMemberid") String SenderMemberid,
                @Field("ReceiverMemberId") String ReceiverMemberId,
                @Field("Status") String Status
        );
    }

    public interface AcceptInvitationOps{
        void acceptInvitation(String SenderMemberid, String ReceiverMemberId, String Status);

        void onDataReceived(Entity entity);
    }

    public interface AcceptInvitationView extends MVPView{
        void showInvitationAccepted(String message);
    }
}