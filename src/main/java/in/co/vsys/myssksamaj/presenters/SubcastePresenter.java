package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.SubcasteContract;
import in.co.vsys.myssksamaj.model.responses.SubcasteResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class SubcastePresenter implements SubcasteContract.SubcasteOps {

    private SubcasteContract.SubcasteAPI subcasteAPI;
    private SubcasteContract.SubcasteView subcasteView;

    public SubcastePresenter(SubcasteContract.SubcasteView subcasteView) {
        this.subcasteView = subcasteView;
        subcasteAPI = RestAdapterContainer.getInstance().create(SubcasteContract.SubcasteAPI.class);
    }

    @Override
    public void getAllSubCasteByCaste(String casteId) {
        subcasteView.showLoading();
        subcasteAPI.getAllSubCasteByCaste(casteId).enqueue(new Callback<SubcasteResponse>() {
            @Override
            public void onResponse(Call<SubcasteResponse> call, Response<SubcasteResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<SubcasteResponse> call, Throwable t) {
                subcasteView.hideLoading();
                subcasteView.showError("Response not found" );
            }
        });
    }

    @Override
    public void onDataReceived(SubcasteResponse subcasteResponse) {
        subcasteView.hideLoading();
        subcasteView.showSubcasteList(subcasteResponse.getData());
    }
}