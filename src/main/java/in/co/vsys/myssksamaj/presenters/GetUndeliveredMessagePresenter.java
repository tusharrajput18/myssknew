package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GetUndeliveredMessageContract;
import in.co.vsys.myssksamaj.model.responses.ChatResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUndeliveredMessagePresenter implements GetUndeliveredMessageContract.getUndeliveredMessageOPS {

    private GetUndeliveredMessageContract.getUndeliveredMessageView mUndeliveredMessageView;
    private GetUndeliveredMessageContract.getUndeliveredMessageAPI mUndeliveredMessageAPI;

    public GetUndeliveredMessagePresenter(GetUndeliveredMessageContract.getUndeliveredMessageView mUndeliveredMessageView) {
        this.mUndeliveredMessageView = mUndeliveredMessageView;
        mUndeliveredMessageAPI = RestAdapterContainer.getInstance().create(GetUndeliveredMessageContract.getUndeliveredMessageAPI.class);
    }

    @Override
    public void getUndeliveredMessage(String memberId) {
        mUndeliveredMessageView.showLoading();
        mUndeliveredMessageAPI.getUndeliveredMessage(memberId).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                onDataRecived(response.body());
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                mUndeliveredMessageView.hideLoading();
                mUndeliveredMessageView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDataRecived(ChatResponse response) {
        mUndeliveredMessageView.showAllChatResponse(response.getData());
    }
}
