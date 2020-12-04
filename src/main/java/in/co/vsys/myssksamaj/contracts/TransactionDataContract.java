package in.co.vsys.myssksamaj.contracts;

import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.model.responses.TransactionDataResponse;
import in.co.vsys.myssksamaj.views.MVPView;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author abhijeetjadhav
 */
public class TransactionDataContract {

    public interface TransactionAPI {
        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/Transaction_insert")
        Call<TransactionDataResponse> getTransactionData(@Field("memberId") String memberId,
                                                         @Field("packageId") String packageId);

        @FormUrlEncoded
        @POST("/matrimonyapp.asmx/Transaction_update")
        Call<Entity> updateTransactionData(@Field("memberId") String memberId,
                                           @Field("packageId") String packageId,
                                           @Field("transactionId") String transactionId,
                                           @Field("status") String status);
    }

    public interface TransactionOps {
        void getTransactionData(String memberId, String packageId);

        void updateTransactionData(String memberId, String packageId, String transactionId, String status);

        void onDataReceived(Entity entity);

        void onDataReceived(TransactionDataResponse transactionDataResponse);
    }

    public interface TransactionView extends MVPView {
        void showTransactionData(String transactionId, String amount);

        void showTransactionUpdate(String message);
    }
}