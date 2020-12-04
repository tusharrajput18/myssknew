package in.co.vsys.myssksamaj.presenters;

import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.responses.TransactionDataResponse;
import in.co.vsys.myssksamaj.model.rest_api.RestAdapterContainer;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author abhijeetjadhav
 */
public class TransactionPresenter implements TransactionDataContract.TransactionOps {

    private TransactionDataContract.TransactionAPI transactionDataAPI;
    private TransactionDataContract.TransactionView transactionView;

    public TransactionPresenter(TransactionDataContract.TransactionView transactionView) {
        this.transactionView = transactionView;
        transactionDataAPI = RestAdapterContainer.getInstance().create(TransactionDataContract.TransactionAPI.class);
    }

    @Override
    public void getTransactionData(String memberId, String packageId) {
        transactionView.showLoading();
        transactionDataAPI.getTransactionData(memberId, packageId).enqueue(new Callback<TransactionDataResponse>() {
            @Override
            public void onResponse(Call<TransactionDataResponse> call, Response<TransactionDataResponse> response) {
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<TransactionDataResponse> call, Throwable t) {
                transactionView.hideLoading();
                transactionView.showError("Could not update transaction");
            }
        });
    }

    @Override
    public void updateTransactionData(String memberId, String packageId, String transactionId, String status) {
        transactionView.showLoading();
        transactionDataAPI.updateTransactionData(memberId, packageId, transactionId, status).enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {
                if (response == null) {
                    transactionView.hideLoading();
                    transactionView.showTransactionUpdate("Could not update transaction");
                    return;
                }
                onDataReceived(response.body());
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                transactionView.hideLoading();
                transactionView.showTransactionUpdate("Could not update transaction");
            }
        });
    }

    @Override
    public void onDataReceived(Entity entity) {
        try {
            transactionView.hideLoading();
            if (entity.getMessage() == null) {
                transactionView.showTransactionUpdate("");
                return;
            }

            transactionView.showTransactionUpdate(entity.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            transactionView.showTransactionUpdate("");
        }
    }

    @Override
    public void onDataReceived(TransactionDataResponse transactionDataResponse) {
        transactionView.hideLoading();
        try {
            if (transactionDataResponse == null || Utilities.isListEmpty(transactionDataResponse.getData())) {
                transactionView.showError(Constants.ERROR_HANDLING.NO_DATA_FOUND);
                return;
            }
            transactionView.showTransactionData(transactionDataResponse.getData().get(0).getTransactionId(),
                    transactionDataResponse.getData().get(0).getAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}