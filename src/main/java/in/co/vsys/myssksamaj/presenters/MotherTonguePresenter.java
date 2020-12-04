package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.MotherTongueContract;
import in.co.vsys.myssksamaj.model.responses.MotherTongueResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class MotherTonguePresenter implements MotherTongueContract.MotherTongueOps {

    private MotherTongueContract.MotherTongueAPI motherTongueAPI;
    private MotherTongueContract.MotherTongueView motherTongueView;

    public MotherTonguePresenter(MotherTongueContract.MotherTongueView motherTongueView) {
        this.motherTongueView = motherTongueView;
        motherTongueAPI = RestAdapterContainer.getInstance().create(MotherTongueContract.MotherTongueAPI.class);
    }

    @Override
    public void getAllMotherTongue() {
        motherTongueView.showLoading();
        motherTongueAPI.getAllMotherTongue().enqueue(new Callback<MotherTongueResponse>() {
            @Override
            public void onResponse(Call<MotherTongueResponse> call, Response<MotherTongueResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<MotherTongueResponse> call, Throwable t) {
                motherTongueView.hideLoading();
                motherTongueView.showError("No results found");
            }
        });
    }

    @Override
    public void onDataReceived(MotherTongueResponse motherTongueResponse) {
        motherTongueView.hideLoading();
        motherTongueView.showMotherTongues(motherTongueResponse.getData());
    }
}