package in.co.vsys.myssksamaj.presenters;

import android.util.Log;

import in.co.vsys.myssksamaj.contracts.AcceptInvitationContract;
import in.co.vsys.myssksamaj.contracts.NumberIfExistContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class NumberifExistPresenter implements NumberIfExistContract.NumberifExistOps{

    NumberIfExistContract.NumberifExistApi numberifExistApi;
    NumberIfExistContract.NumberIfExistview numberIfExistview;

    private AcceptInvitationContract.AcceptInvitationAPI acceptInvitationAPI;
    private AcceptInvitationContract.AcceptInvitationView acceptInvitationView;

    public NumberifExistPresenter(NumberIfExistContract.NumberIfExistview numberIfExistview) {
        this.numberIfExistview = numberIfExistview;

        numberifExistApi = RestAdapterContainer.getInstance().create(NumberIfExistContract.NumberifExistApi.class);
    }

    @Override
    public void numberifexist(String Mobile) {
        numberIfExistview.showLoading();
        numberifExistApi.numberexit(Mobile).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                onNumberDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                numberIfExistview.hideLoading();
                numberIfExistview.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);

            }
        });


    }

    @Override
    public void onNumberDataReceived(Entity entity) {
        numberIfExistview.hideLoading();
        if(entity==null){
            numberIfExistview.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
            return;
        }
        numberIfExistview.showNumbermsg(entity.getMessage());
    }
}
