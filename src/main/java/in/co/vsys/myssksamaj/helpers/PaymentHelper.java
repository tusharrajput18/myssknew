package in.co.vsys.myssksamaj.helpers;

/**
 * @author abhijeetjadhav
 */
public class PaymentHelper {


    //    public static String MerchantKey = "8QK4T3BS2T";
    public static String MerchantKey = "AFKT70AHMM";
    //    public static String Salt = "WVOW4X6B65";
    public static String Salt = "V9I6WLW4SH";
    //    public static String payment_mode = "test";
    public static String payment_mode = "production";
    public static int merchant_is_coupon_enabled = 0;

    public static String GET_PREMIUM = "Only premium members can view, invite and chat. Proceed to purchase premium subscription.";

    private static PaymentHelper instance;

    private PaymentHelper() {
    }

    public static PaymentHelper getInstance() {
        if (instance == null)
            instance = new PaymentHelper();
        return instance;
    }
}
