package in.co.vsys.myssksamaj.presenters;

import android.util.Log;

import in.co.vsys.myssksamaj.contracts.ChatThreadContract;
import in.co.vsys.myssksamaj.model.responses.ChatThreadsResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class ChatThreadPresenter implements ChatThreadContract.ChatThreadOps {

    private ChatThreadContract.ChatThreadAPI chatThreadAPI;
    private ChatThreadContract.ChatThreadView chatThreadView;

    public ChatThreadPresenter(ChatThreadContract.ChatThreadView chatThreadView) {
        this.chatThreadView = chatThreadView;
        chatThreadAPI = RestAdapterContainer.getInstance().create(ChatThreadContract.ChatThreadAPI.class);
    }

    @Override
    public void getMyChatThreads(String memberId) {
        Log.d("mytag", "getMyChatThreads: "+memberId);
        chatThreadView.showLoading();
        chatThreadAPI.getMyChatThreads(memberId).enqueue(new Callback<ChatThreadsResponse>() {
            @Override
            public void onResponse(Call<ChatThreadsResponse> call, Response<ChatThreadsResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<ChatThreadsResponse> call, Throwable t) {
                chatThreadView.hideLoading();
                chatThreadView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            }
        });
    }

    @Override
    public void onDataReceived(ChatThreadsResponse chatThreadsResponse) {
        chatThreadView.hideLoading();
        if (chatThreadsResponse == null || Utilities.isListEmpty(chatThreadsResponse.getData())) {
            chatThreadView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        chatThreadView.showChatThreads(chatThreadsResponse.getData());

    }
}