package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.GotraContract;
import in.co.vsys.myssksamaj.model.responses.GotraResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class GotraPresenter implements GotraContract.GotraOps {

    private GotraContract.GotraAPI gotraAPI;
    private GotraContract.GotraView gotraView;

    public GotraPresenter(GotraContract.GotraView gotraView) {
        this.gotraView = gotraView;
        gotraAPI = RestAdapterContainer.getInstance().create(GotraContract.GotraAPI.class);
    }

    @Override
    public void getAllGotra() {
        gotraView.showLoading();
        gotraAPI.getAllGotra().enqueue(new Callback<GotraResponse>() {
            @Override
            public void onResponse(Call<GotraResponse> call, Response<GotraResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<GotraResponse> call, Throwable t) {
                gotraView.hideLoading();
                gotraView.showError("No results found" );
            }
        });

    }

    @Override
    public void onDataReceived(GotraResponse gotraResponse) {
        gotraView.hideLoading();
        if (gotraResponse == null || Utilities.isListEmpty(gotraResponse.getGotraModels())) {
            gotraView.showError("No results found" );
            return;
        }
        gotraView.showAllGotra(gotraResponse.getGotraModels());

    }
}
