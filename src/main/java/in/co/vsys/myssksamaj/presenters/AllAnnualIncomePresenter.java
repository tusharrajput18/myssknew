package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.AllAnnualIncomeContract;
import in.co.vsys.myssksamaj.model.responses.AllAnnualIncomeResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAnnualIncomePresenter implements AllAnnualIncomeContract.AllAnnualIncomeOPS {

    private AllAnnualIncomeContract.AllAnnualIncomeView mAllAnnualIncomeView;
    private AllAnnualIncomeContract.AllAnnualIncomeAPI mAllAnnualIncomeAPI;

    public AllAnnualIncomePresenter(AllAnnualIncomeContract.AllAnnualIncomeView mAllAnnualIncomeView) {
        this.mAllAnnualIncomeView = mAllAnnualIncomeView;
        mAllAnnualIncomeAPI = RestAdapterContainer.getInstance().create(AllAnnualIncomeContract.AllAnnualIncomeAPI.class);
    }

    @Override
    public void allAnnualIncome() {
        mAllAnnualIncomeView.showLoading();
        mAllAnnualIncomeAPI.allAnnualIncome().enqueue(new Callback<AllAnnualIncomeResponse>() {
            @Override
            public void onResponse(Call<AllAnnualIncomeResponse> call, Response<AllAnnualIncomeResponse> response) {
                onDataRecieved(response.body());
            }

            @Override
            public void onFailure(Call<AllAnnualIncomeResponse> call, Throwable t) {
                mAllAnnualIncomeView.hideLoading();
                mAllAnnualIncomeView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDataRecieved(AllAnnualIncomeResponse response) {
            mAllAnnualIncomeView.hideLoading();
            mAllAnnualIncomeView.showAnnualIncome(response.getData());



    }
}
