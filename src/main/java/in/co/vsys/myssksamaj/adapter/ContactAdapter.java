package in.co.vsys.myssksamaj.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.model.ContactsModel;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    List<ContactsModel> contactsModelList;
    Context context;


    public ContactAdapter(Context context, List<ContactsModel> contactsModelList) {

        this.context=context;
        this.contactsModelList=contactsModelList;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_matrimony_contact_list, viewGroup, false);
        ContactViewHolder viewHolder = new ContactViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {

        ContactsModel contactsModel=contactsModelList.get(i);
        contactViewHolder.row_contact_Name.setText(contactsModel.getFirstName()+" "+contactsModel.getLastName());
        contactViewHolder.row_contact_number.setText(contactsModel.getMobile());

    }

    @Override
    public int getItemCount() {
        return contactsModelList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView row_contact_Name,row_contact_number;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            row_contact_Name=(TextView)itemView.findViewById(R.id.row_contact_Name);
            row_contact_number=(TextView)itemView.findViewById(R.id.row_contact_number);
        }
    }
}
