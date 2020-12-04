package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class UpdateMsgSentToDeliveredContract {

    public interface UpdateMsgDeliveredAPI {
        @GET("matrimonyapp.asmx/updateMsgSentToDelivered")
        Call<Entity> updateMsgDelivered(@Query("ReceiverId") String receiverId);
    }

    public interface UpdateMsgDeliveredOPS {
        void updateMsgDelivered(String receiverId);

        void onDataRecived(Entity entity);
    }

    public interface UpdateMsgDeliveredView extends MVPView {
        void showResponse(Entity entity);
    }

}
