package in.co.vsys.myssksamaj.contracts;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.ChatModel;
import in.co.vsys.myssksamaj.model.responses.ChatResponse;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.views.MVPView;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author abhijeetjadhav
 */
public class ChatContract {

    public interface ChatAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/insert_chat")
        Call<Entity> insertChat(
                @Field("senderId") int senderId,
                @Field("receiverTypeId") int receiverTypeId,
                @Field("receiverId") int receiverId,
                @Field("messageTypeId") int messageTypeId,
                @Field("Message") String Message,
                @Field("parentId") int parentId,
                @Field("messageStatusId") int messageStatusId
        );

        @GET("/matrimonyapp.asmx/getChat")
        Call<ChatResponse> getChat(@Query("userId1") String userId1, @Query("userId2") String userId2,
                                   @Query("page") String page);

        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/delete_chat")
        Call<Entity> deleteChat(@Field("chatId") int chatId);
    }

    public interface ChatOps {
        void insertChat(int senderId, int receiverTypeId, int receiverId, int messageTypeId,
                        String Message, int parentId, int messageStatusId);

        void getChat(String userId1, String userId2, String page);

        void getRecentChat(String userId1, String userId2, String page);

        void deleteChat(int chatId);

        void onDeleteDataReceived(Entity entity);

        void onDataReceived(Entity entity);

        void onDataReceived(ChatResponse chatResponse);

        void onRecentDataReceived(ChatResponse chatResponse);
    }

    public interface ChatView extends MVPView {
        void reloadRecentChat();

        void showChatInserted(String message);

        void showChatDeleted(String message);

        void showChat(List<ChatModel> chatModels);

        void showRecentChat(List<ChatModel> chatModels);

        String getChatId();
    }
}