package in.co.vsys.myssksamaj.model.responses;

import java.io.Serializable;
import java.util.List;

/**
 * @author abhijeetjadhav
 */
public class TransactionDataResponse extends Entity implements Serializable {
    private List<TransactionDataModel> data;

    public List<TransactionDataModel> getData() {
        return data;
    }

    public void setData(List<TransactionDataModel> data) {
        this.data = data;
    }

    public class TransactionDataModel {
        private String transactionId, amount;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}