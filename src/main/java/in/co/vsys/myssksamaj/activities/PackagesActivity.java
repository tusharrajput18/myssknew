package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomSpinnerAdapter;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.model.data_models.PackageModel;
import in.co.vsys.myssksamaj.presenters.AllPackagesPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class PackagesActivity extends AppCompatActivity {

    private Context mContext;
    private Spinner packageSpinner;
    private String selectedPackageId = "", amount = "", description;
    private TextView packageDescription, packageAmount;
    private int mMemberId, mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        mContext = this;

        mMemberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        packageSpinner = findViewById(R.id.packages_spinner);
        packageDescription = findViewById(R.id.package_desc);
        packageAmount = findViewById(R.id.amount);

        new AllPackagesPresenter(new AllPackagesPresenter.AllPackagesView() {
            @Override
            public Context getContext() {
                return mContext;
            }

            @Override
            public void showPackages(List<PackageModel> packageModels) {
                showPackagesSpinner(packageModels);
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
        });

        findViewById(R.id.buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.isEmpty(amount)) {
                    Utilities.showLongToast(mContext, "Please select a package");
                    return;
                }
                PaymentActivity.startPaymentActivity(PackagesActivity.this, PaymentActivity.REQUEST_CODE,
                        mMemberId, Utilities.getInt(selectedPackageId));
            }
        });
    }

    private void showPackagesSpinner(List<PackageModel> packageModels) {
        try {
            CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext,
                    packageModels, R.layout.spinner_layout_item, new CustomSpinnerAdapter.ItemClickedListener() {
                @Override
                public void onViewBound(View view, Object object, int position) {
                    try {
                        PackageModel packageModel = (PackageModel) object;
                        TextView textView = view.findViewById(R.id.text1);
                        Utilities.setText(textView, packageModel.getPackagename());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            packageSpinner.setAdapter(customSpinnerAdapter);
            packageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    PackageModel packageModel = (PackageModel) parent.getAdapter().getItem(position);
                    selectedPackageId = packageModel.getPackageid();
                    amount = packageModel.getAmt();
                    description = packageModel.getPackagedescription();

                    Utilities.setText(packageDescription, packageModel.getPackagedescription());
                    Utilities.setText(packageAmount,
                            Constants.RUPEE_SYMBOL + (Utilities.isEmpty(packageModel.getAmt()) ? " 0" : packageModel.getAmt())
                    );
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == PaymentActivity.REQUEST_CODE) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
