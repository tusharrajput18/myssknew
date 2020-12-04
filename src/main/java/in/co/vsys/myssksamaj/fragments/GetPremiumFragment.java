package in.co.vsys.myssksamaj.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import in.co.vsys.myssksamaj.interfaces.PerformPaymentListener;

/**
 * @author Abhijeet.J
 */

public class GetPremiumFragment extends DialogFragment {

    private Context context;
    private int selectedPosition = 0;
    private PerformPaymentListener mPerformPaymentListener;

    public void setPerformPaymentListener(PerformPaymentListener performPaymentListener) {
        this.mPerformPaymentListener = performPaymentListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        readIntent();
        context = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

//        dialog.
//        View view = LayoutInflater.from(context).inflate(R.layout.assign_employee, null);
//        initUI(view);
//        dialog.setContentView(view);

//        showEmployees();

        return dialog;
    }

//    private void showEmployees() {
//        if (customListAdapter == null) {
//            customListAdapter = new CustomListAdapter(context, recyclerView, employees, R.layout.employee_list_item,
//                    this);
//            recyclerView.setAdapter(customListAdapter);
//        } else {
//            customListAdapter.updateList(employees);
//            customListAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private void initUI(View view) {
//        recyclerView = view.findViewById(R.id.employee_list);
//
//        TextView add = view.findViewById(R.id.add);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                employeeSelectionListener.onEmployeeSelected(mSelectedUserModel, selectedPosition);
//                dismiss();
//            }
//        });
//    }
}