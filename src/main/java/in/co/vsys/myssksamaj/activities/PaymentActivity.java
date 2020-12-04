package in.co.vsys.myssksamaj.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.Utilities;


public class PaymentActivity extends AppCompatActivity implements TransactionDataContract.TransactionView {

    private Context mContext;
    private TransactionDataContract.TransactionOps transactionDataPresenter;
    private int mMemberId, mPackageId = 1;
    private String mTransactionId = "";
    public static int REQUEST_CODE = 1213;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_payment);
        mContext = this;

        readIntent();

        transactionDataPresenter = new TransactionPresenter(this);
        transactionDataPresenter.getTransactionData("" + mMemberId, "" + mPackageId);
    }

    private void readIntent() {
        if (!Utilities.isIntentPresent(getIntent()))
            return;

        if (getIntent().getExtras().containsKey(Constants.MEMBER_ID))
            mMemberId = getIntent().getIntExtra(Constants.MEMBER_ID, 0);

        if (getIntent().getExtras().containsKey(Constants.PACKAGE_ID))
            mPackageId = getIntent().getIntExtra(Constants.PACKAGE_ID, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getStringExtra("result");

        String status;

        /**
         * Note:
         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
         * for other constants check the documentation of easebuzz.in here:
         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
         */
        if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
            status = "1";
            successfulTransactionCompletion();
        } else {
            status = "0";
            transactionFailure();
        }

        transactionDataPresenter.updateTransactionData("" + mMemberId, "" + mPackageId,
                mTransactionId, status);
    }

    // When payment is done through easebuzz then invoke this method.

    // This will set SharedPreferences and update the drawer view with appropriate "Paid/Premium member"
    private void successfulTransactionCompletion() {
        try {
            MainPresenter.getInstance().setUserType(mContext, 1);
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionFailure() {
        try {
            MainPresenter.getInstance().setUserType(mContext, 0);
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();

            setResult(RESULT_CANCELED);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTransactionData(String transactionId, String amount) {
        try {
            mTransactionId = transactionId;
            float fAmount = Float.parseFloat(amount);

            String memberId = "" + MainPresenter.getInstance().getMemberId(mContext);
            String memberName = MainPresenter.getInstance().getMemberName(mContext);
            String emailId = MainPresenter.getInstance().getEmailId(mContext);
            String phone = MainPresenter.getInstance().getPhone(mContext);
            String productInfo = "Yearly subscription";

            initiatePayment(mContext, memberId, transactionId, fAmount, productInfo, memberName, emailId, phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiatePayment(Context context, String memberId, String transactionId, float amount,
                                String productInfo, String firstName, String emailId,
                                String phone) {
        try {
            Intent intent = new Intent(context, PWECouponsActivity.class);

            intent.putExtra("trxn_id", transactionId);
            intent.putExtra("trxn_amount", amount);
            intent.putExtra("trxn_prod_info", productInfo);
            intent.putExtra("trxn_firstname", firstName);
            intent.putExtra("trxn_email_id", emailId);
            intent.putExtra("trxn_phone", phone);
            intent.putExtra("trxn_key", PaymentHelper.MerchantKey);
            intent.putExtra("trxn_is_coupon_enabled", PaymentHelper.merchant_is_coupon_enabled);
            intent.putExtra("trxn_salt", PaymentHelper.Salt);
            intent.putExtra("unique_id", memberId);
            intent.putExtra("enable_save_card", 1);
            intent.putExtra("pay_mode", PaymentHelper.payment_mode);

            startActivityForResult(intent, StaticDataModel.PWE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTransactionUpdate(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    public static void startPaymentActivity(Activity activity, int requestCode, int memberId, int packageId) {
        try {
            Intent intent = new Intent(activity, PaymentActivity.class);
            intent.putExtra(Constants.MEMBER_ID, memberId);
            intent.putExtra(Constants.PACKAGE_ID, packageId);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}