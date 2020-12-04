package in.co.vsys.myssksamaj.matrimonyguest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.maindirectory.LoginOptionsActivity;
import in.co.vsys.myssksamaj.model.RecentlyJointModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;

/**
 * Created by Jack on 05-Feb-18.
 */
public class GuestFilterAdapter extends RecyclerView.Adapter<GuestFilterAdapter.ViewHolder> {

    private List<UserProfileModel> list;
    private Context mContext;
    private int userMemeberId, memberId, shortSuccess;
    private SharedPreferences mPreferences;

    public GuestFilterAdapter(Context mContext, List<UserProfileModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GuestFilterAdapter.ViewHolder holder, final int position) {

        final UserProfileModel jointModel = list.get(position);


        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        memberId = mPreferences.getInt("memberId", 0);

       /* if (TextUtils.isEmpty(jointModel.getShortlistedFlag()) || jointModel.getShortlistedFlag().contains("0")) {

            holder.img_shortlist.setVisibility(View.VISIBLE);
            holder.img_shortlisted.setVisibility(View.GONE);
            holder.tv_shortlist.setVisibility(View.VISIBLE);
            holder.tv_shortlisted.setVisibility(View.GONE);

        } else {

            holder.img_shortlisted.setVisibility(View.VISIBLE);
            holder.img_shortlist.setVisibility(View.GONE);
            holder.tv_shortlist.setVisibility(View.GONE);
            holder.tv_shortlisted.setVisibility(View.VISIBLE);

        }

        if (TextUtils.isEmpty(jointModel.getInvitedFlag()) || jointModel.getInvitedFlag().contains("0")) {

            holder.img_invite.setVisibility(View.VISIBLE);
            holder.img_invited.setVisibility(View.GONE);
            holder.tv_invite.setVisibility(View.VISIBLE);
            holder.tv_invited.setVisibility(View.GONE);

        } else {

            holder.img_invited.setVisibility(View.VISIBLE);
            holder.img_invite.setVisibility(View.GONE);
            holder.tv_invite.setVisibility(View.GONE);
            holder.tv_invited.setVisibility(View.VISIBLE);

        }*/


        holder.tv_userId.setText(jointModel.getUniqueId());
        holder.tv_userAge_height.setVisibility(View.GONE);
        holder.tv_motherTounge.setVisibility(View.GONE);
        holder.tv_city_state_country.setVisibility(View.GONE);
        holder.tv_married_status.setVisibility(View.GONE);
        holder.tv_profession.setVisibility(View.GONE);
        holder.tv_income.setVisibility(View.GONE);

        holder.linearLayout.setVisibility(View.GONE);

        if (jointModel.getMainProfilePhoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(holder.profileUrl);
        } else {

            Picasso.get()
                    .load(jointModel.getMainProfilePhoto())
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(holder.profileUrl);
        }

        holder.profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginAlertDialog();
            }
        });

        /*try {

            int year = Calendar.getInstance().get(Calendar.YEAR);

            String dob = jointModel.getDOB();

            String[] dob1 = dob.split("/");

            String dateOfBirth = dob1[2];

            int age = (year - Integer.parseInt(dateOfBirth));

            jointModel.setUserAge("" + age);

        } catch (ArrayIndexOutOfBoundsException e) {

            e.printStackTrace();
        }


        String age1 = "" + jointModel.getUserAge();
        String height = "" + jointModel.getUserHeight();
        String age_height;


        if (age1.equals("") || age1.equalsIgnoreCase("")) {

            age_height = "" + height;
        } else {

            age_height = age1 + " , " + height;
        }

        if (age_height.equals("") || age_height.equalsIgnoreCase("")) {

            holder.tv_userAge_height.setVisibility(View.GONE);

        } else {

            holder.tv_userAge_height.setVisibility(View.VISIBLE);
            holder.tv_userAge_height.setText(age_height);
        }

        if (jointModel.getMotherTongue().equals("") || jointModel.getMotherTongue().equalsIgnoreCase("")) {

            holder.tv_motherTounge.setVisibility(View.GONE);

        } else {

            holder.tv_motherTounge.setVisibility(View.VISIBLE);
            holder.tv_motherTounge.setText(jointModel.getMotherTongue());
        }

        String city = "" + jointModel.getUserCity();
        String country = "" + jointModel.getUserCountry();
        String city_country;

        if (city.equals("") || city.equalsIgnoreCase("")) {

            city_country = "" + country;
        } else {

            city_country = city + "," + country;
        }


        if (city_country.equals("") || city_country.equalsIgnoreCase("")) {

            holder.tv_city_state_country.setVisibility(View.GONE);
        } else {

            holder.tv_city_state_country.setVisibility(View.VISIBLE);
            holder.tv_city_state_country.setText(city_country);
        }


        if (jointModel.getMarriedStatus().equals("") || jointModel.getMarriedStatus().equalsIgnoreCase("")) {

            holder.tv_married_status.setVisibility(View.GONE);

        } else {

            holder.tv_married_status.setVisibility(View.VISIBLE);
            holder.tv_married_status.setText(jointModel.getMarriedStatus());
        }

        if (jointModel.getEductionIn().equals("") || jointModel.getEductionIn().equalsIgnoreCase("")) {

            holder.tv_profession.setVisibility(View.GONE);

        } else {

            holder.tv_profession.setVisibility(View.VISIBLE);
            holder.tv_profession.setText(jointModel.getEductionIn());
        }

        if (jointModel.getUserIncome().equals("") || jointModel.getUserIncome().equalsIgnoreCase("")) {

            holder.tv_income.setVisibility(View.GONE);

        } else {

            holder.tv_income.setVisibility(View.VISIBLE);
            holder.tv_income.setText(jointModel.getUserIncome());
        }


        if (jointModel.getUserProfileUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(holder.profileUrl);
        } else {

            Picasso.get()
                    .load("http://app.myssksamaj.com/uploads/mainprofilepic/" + jointModel.getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(holder.profileUrl);
        }

        holder.profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, GuestSearchDisplayActivity.class);
                String uniqueId = jointModel.getUniqueId();

                mPreferences.edit().putString("uniqueId", uniqueId).apply();

                mContext.startActivity(intent);
            }
        });

        holder.img_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = list.get(position).getUserMemberId();
                matrimonyUtils.addShortlistTask(memberId, senderId, "1");


                holder.img_shortlisted.setVisibility(View.VISIBLE);
                holder.img_shortlist.setVisibility(View.GONE);

                holder.tv_shortlist.setVisibility(View.GONE);
                holder.tv_shortlisted.setVisibility(View.VISIBLE);

            }
        });

        holder.img_shortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = list.get(position).getUserMemberId();
                matrimonyUtils.addShortlistTask(memberId, senderId, "0");


                holder.img_shortlist.setVisibility(View.VISIBLE);
                holder.img_shortlisted.setVisibility(View.GONE);

                holder.tv_shortlist.setVisibility(View.VISIBLE);
                holder.tv_shortlisted.setVisibility(View.GONE);

            }
        });

        holder.img_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = list.get(position).getUserMemberId();
                matrimonyUtils.inviteTask(memberId, senderId, "1");


                holder.img_invited.setVisibility(View.VISIBLE);
                holder.img_invite.setVisibility(View.GONE);

                holder.tv_invite.setVisibility(View.GONE);
                holder.tv_invited.setVisibility(View.VISIBLE);


            }
        });

        holder.img_invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int senderId = list.get(position).getUserMemberId();
                matrimonyUtils.inviteTask(memberId, senderId, "0");

                holder.img_invite.setVisibility(View.VISIBLE);
                holder.img_invited.setVisibility(View.GONE);

                holder.tv_invite.setVisibility(View.VISIBLE);
                holder.tv_invited.setVisibility(View.GONE);


            }
        });

        holder.img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private ImageView profileUrl, img_invite, img_invited, img_shortlisted, img_shortlist, img_chat;
        private TextView tv_shortlist, tv_shortlisted, tv_userId, tv_userAge_height, tv_motherTounge, tv_married_status, tv_profession, tv_income, tv_city_state_country, tv_invite, tv_invited;

        ViewHolder(View itemView) {
            super(itemView);

            profileUrl = itemView.findViewById(R.id.img_scroll_profileImage);
            img_invite = itemView.findViewById(R.id.img_scroll_invite);
            img_invited = itemView.findViewById(R.id.img_scroll_invited);
            img_shortlist = itemView.findViewById(R.id.img_scroll_shortlist);
            img_shortlisted = itemView.findViewById(R.id.img_scroll_shortlisted);
            img_chat = itemView.findViewById(R.id.img_scroll_chat);
            tv_userId = itemView.findViewById(R.id.tv_scroll_memberId);
            tv_userAge_height = itemView.findViewById(R.id.tv_scroll_profile_otherDetails);
            tv_motherTounge = itemView.findViewById(R.id.tv_scroll_profile_languageDetails);
            tv_profession = itemView.findViewById(R.id.tv_scroll_profile_ProfessionDetails);
            tv_income = itemView.findViewById(R.id.tv_scroll_profile_saleryDetails);
            tv_married_status = itemView.findViewById(R.id.tv_scroll_profile_marriedStatus1);
            tv_city_state_country = itemView.findViewById(R.id.tv_scroll_profile_AddressDetails);
            tv_shortlist = itemView.findViewById(R.id.tv_scroll_shortlist);
            tv_shortlisted = itemView.findViewById(R.id.tv_scroll_shortlisted);
            tv_invite = itemView.findViewById(R.id.tv_scroll_invite);
            tv_invited = itemView.findViewById(R.id.tv_scroll_invited);
            linearLayout = itemView.findViewById(R.id.linearLayout);


        }

    }

    private void loginAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row_matri_guest_login, null);
        builder.setView(view);

        AppCompatButton btn_login = view.findViewById(R.id.row_btn_guestLogin);
        AppCompatButton btn_register = view.findViewById(R.id.row_btn_guestRegister);

        final AlertDialog alertDialog = builder.create();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, LoginOptionsActivity.class));
                alertDialog.dismiss();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, LoginOptionsActivity.class));
                alertDialog.dismiss();

            }
        });

        alertDialog.show();


    }
}
