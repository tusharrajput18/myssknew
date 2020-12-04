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
public class ShortListContract {
    public interface ShortListAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/Member_ShortListUpdateStatus")
        Call<Entity> performShortlist(
                @Field("SenderMemberid") String SenderMemberid,
                @Field("ReceiverMemberId") String ReceiverMemberId,
                @Field("Status") String Status
        );
    }

    public interface ShortListOps {
        void performShortlist(String SenderMemberid, String ReceiverMemberId, String Status);

        void onDataReceived(Entity entity);
    }

    public interface ShortListView extends MVPView {
        void showShortlisted(String message);
    }
}
