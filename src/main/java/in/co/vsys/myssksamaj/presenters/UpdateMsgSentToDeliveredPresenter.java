package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.UpdateMsgSentToDeliveredContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMsgSentToDeliveredPresenter implements UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredOPS {

    private UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredView mUpdateMsgDeliveredView;
    private UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredAPI mUpdateMsgDeliveredAPI;

    public UpdateMsgSentToDeliveredPresenter(UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredView mUpdateMsgDeliveredView) {
        this.mUpdateMsgDeliveredView = mUpdateMsgDeliveredView;
        mUpdateMsgDeliveredAPI = RestAdapterContainer.getInstance().create(UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredAPI.class);
    }

    @Override
    public void updateMsgDelivered(String receiverId) {
        mUpdateMsgDeliveredView.showLoading();
        mUpdateMsgDeliveredAPI.updateMsgDelivered(receiverId).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onDataRecived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable throwable) {
                mUpdateMsgDeliveredView.hideLoading();
                mUpdateMsgDeliveredView.showError(throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDataRecived(Entity entity) {
        mUpdateMsgDeliveredView.hideLoading();
        mUpdateMsgDeliveredView.showResponse(entity);
    }
}
