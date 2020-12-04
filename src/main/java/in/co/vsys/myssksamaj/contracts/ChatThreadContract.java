package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.ChatThreadModel;
import in.co.vsys.myssksamaj.model.responses.ChatThreadsResponse;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class ChatThreadContract {

    public interface ChatThreadAPI {
        @GET("/matrimonyapp.asmx/getMyChatThreads")
        Call<ChatThreadsResponse> getMyChatThreads(@Query("memberId") String memberId);
    }

    public interface ChatThreadOps {
        void getMyChatThreads(String memberId);

        void onDataReceived(ChatThreadsResponse chatThreadsResponse);
    }

    public interface ChatThreadView extends MVPView {
        void showChatThreads(List<ChatThreadModel> chatThreadModels);
    }
}