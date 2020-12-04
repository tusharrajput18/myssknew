package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.ChatContract;
import in.co.vsys.myssksamaj.model.responses.ChatResponse;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class ChatPresenter implements ChatContract.ChatOps {

    private ChatContract.ChatAPI mChatAPI;
    private ChatContract.ChatView mChatView;

    public ChatPresenter(ChatContract.ChatView chatView) {
        mChatView = chatView;
        mChatAPI = RestAdapterContainer.getInstance().create(ChatContract.ChatAPI.class);
    }

    @Override
    public void insertChat(int senderId, int receiverTypeId, int receiverId, int messageTypeId,
                           String message, int parentId, int messageStatusId) {
        mChatView.showLoading();
        mChatAPI.insertChat(senderId, receiverTypeId, receiverId, messageTypeId, message, parentId,
                messageStatusId).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                mChatView.hideLoading();
                mChatView.showError(t.getMessage());
            }
        });
    }

    @Override
    public void getChat(String userId1, String userId2, String page) {
        mChatView.showLoading();
        mChatAPI.getChat(userId1, userId2, page).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                mChatView.hideLoading();
                mChatView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void getRecentChat(String userId1, String userId2, String page) {
        mChatView.showLoading();
        mChatAPI.getChat(userId1, userId2, page).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                onRecentDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                mChatView.hideLoading();
                mChatView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void deleteChat(int chatId) {
        mChatView.showLoading();
        mChatAPI.deleteChat(chatId).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDeleteDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                mChatView.hideLoading();
                mChatView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDeleteDataReceived(Entity entity) {
        mChatView.hideLoading();
        mChatView.showChatDeleted(entity.getMessage());
    }

    @Override
    public void onDataReceived(Entity entity) {
        mChatView.hideLoading();
        if (entity == null) {
            mChatView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        mChatView.showChatInserted(entity.getMessage());
    }

    @Override
    public void onDataReceived(ChatResponse chatResponse) {
        mChatView.hideLoading();
        if (chatResponse == null) {
            mChatView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        mChatView.showChat(chatResponse.getData());
    }

    @Override
    public void onRecentDataReceived(ChatResponse chatResponse) {
        mChatView.hideLoading();
        if (chatResponse == null) {
            mChatView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        mChatView.showRecentChat(chatResponse.getData());
    }
}