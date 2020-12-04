package in.co.vsys.myssksamaj.helpers;

/**
 * @author abhijeetjadhav
 */
public class TransactionHelper {
    private static TransactionHelper instance;

    private TransactionHelper() {
    }

    public static TransactionHelper getInstance() {
        if (instance == null)
            instance = new TransactionHelper();
        return instance;
    }


}