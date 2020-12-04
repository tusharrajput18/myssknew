package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.UpdateMsgSentToReadContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMsgSentToReadPresender implements UpdateMsgSentToReadContract.updateMsgSentToReadOPS {

    private UpdateMsgSentToReadContract.updateMsgSentToReadView mUpdateMsgSentToReadView;
    private UpdateMsgSentToReadContract.updateMsgSentToReadAPI mUpdateMsgSentToReadAPI;

    public UpdateMsgSentToReadPresender(UpdateMsgSentToReadContract.updateMsgSentToReadView mUpdateMsgSentToReadView) {
        this.mUpdateMsgSentToReadView = mUpdateMsgSentToReadView;
        mUpdateMsgSentToReadAPI= RestAdapterContainer.getInstance().create(UpdateMsgSentToReadContract.updateMsgSentToReadAPI.class);
    }

    @Override
    public void updateMsgSentToRead(String senderId, final String reciverID) {
        mUpdateMsgSentToReadView.showLoading();
        mUpdateMsgSentToReadAPI.updateMsgSentToRead(senderId, reciverID).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataRecieved(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                mUpdateMsgSentToReadView.hideLoading();
                mUpdateMsgSentToReadView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDataRecieved(Entity entity) {
        mUpdateMsgSentToReadView.showResponse(entity );
    }
}
