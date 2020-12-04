package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class UpdateMsgSentToReadContract {

    public interface updateMsgSentToReadAPI {
        @GET("matrimonyapp.asmx/updateMsgSentToRead")
        Call<Entity> updateMsgSentToRead(@Query("senderId") String senderId, @Query("ReceiverId") String receiverId);
    }

    public interface updateMsgSentToReadOPS {
        void updateMsgSentToRead(String senderId, String reciverID);

        void onDataRecieved(Entity entity);
    }

    public interface updateMsgSentToReadView extends MVPView {
        void showResponse(Entity entity);
    }
}
