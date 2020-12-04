package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.ChatModel;
import in.co.vsys.myssksamaj.model.responses.ChatResponse;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GetUndeliveredMessageContract {

    public interface getUndeliveredMessageAPI {
        @GET("matrimonyapp.asmx/getundeliveredMessage")
        Call<ChatResponse> getUndeliveredMessage(@Query("receiveredId") String senderId);
    }

    public interface getUndeliveredMessageOPS {
        void getUndeliveredMessage(String senderId);

        void onDataRecived(ChatResponse response);
    }

    public interface getUndeliveredMessageView extends MVPView {
        void showAllChatResponse(List<ChatModel> chatModelList);
    }
}
