package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class BlockContract {
    public interface BlockAPI {
        @GET("/matrimonyapp.asmx/Member_BlockedUpdateStatus")
        Call<Entity> updateBlockStatus(@Query("SenderMemberid") int SenderMemberid,
                                       @Query("ReceiverMemberId") int ReceiverMemberId,
                                       @Query("Status") int Status);
    }

    public interface BlockOps {
        void updateBlockStatus(int senderId, int receiverId, int status);

        void onDataReceived(Entity entity);
    }

    public interface BlockView extends MVPView {
        void showBlockedStatus(String message);
    }
}
