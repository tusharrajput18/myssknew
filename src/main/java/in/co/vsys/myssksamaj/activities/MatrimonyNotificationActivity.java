package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.model.NotificationModel;
import in.co.vsys.myssksamaj.utils.MatrimonyDBHelper;
import in.co.vsys.myssksamaj.utils.Utilities;

public class MatrimonyNotificationActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private List<NotificationModel> notificationList;
    private MatrimonyDBHelper matrimonyDBHelper;
    private SharedPreferences mPreferences;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_notification);
        mContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_matriNotification);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_matriNotification);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_matriNotification);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Notification");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        matrimonyDBHelper = new MatrimonyDBHelper(this);
        memberId = mPreferences.getInt("memberId", 0);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        notificationList = new ArrayList<>();
        notificationList = matrimonyDBHelper.notificationList(memberId);

        if (notificationList.size() > 0) {

            CustomListAdapter customListAdapter = new CustomListAdapter(mContext, mRecyclerView, notificationList,
                    R.layout.row_matrimony_notification, new CustomListAdapter.ItemClickedListener() {
                @Override
                public void onViewBound(View itemView, Object object, int position) {

                    NotificationModel notificationModel = notificationList.get(position);
                    if(notificationModel.getNotificationTitle() == null || notificationModel.getNotificationTitle().equalsIgnoreCase("null"))
                        notificationModel.setNotificationTitle(null);

                    if(notificationModel.getNotificationDesc() == null || notificationModel.getNotificationDesc().equalsIgnoreCase("null"))
                        notificationModel.setNotificationDesc(null);

                    CircleImageView imageView;
                    TextView tvTitle, tvDesc, tvTime, tv_profile_created_by;

                    imageView = itemView.findViewById(R.id.row_notificationListImage);
                    tvTitle = itemView.findViewById(R.id.row_notificationListTitle);
                    tvDesc = itemView.findViewById(R.id.row_notificationListDesc);
                    tvTime = itemView.findViewById(R.id.row_notificationListTime);
                    tv_profile_created_by = itemView.findViewById(R.id.tv_profile_created_by);

                    Utilities.setDataAndVisibility(tvTitle, notificationModel.getNotificationTitle());
                    Utilities.setDataAndVisibility(tvDesc, notificationModel.getNotificationDesc());
                    String createdBy = notificationModel.getCreatedBy();

                    if(!Utilities.isEmpty(createdBy)) {
                        Utilities.setDataAndVisibility(tv_profile_created_by,
                                notificationModel.getCreatedBy().equalsIgnoreCase("P") ? "Parent" : "Candidate");
                    }

                    tvTime.setText(DateFormat.format("dd-MM-yyyy (hh:mm a)", Long.valueOf(notificationModel.getNotificationTime())));

                    if (notificationModel.getNotificationImage().length() > 10) {
                        Picasso.get().load(notificationModel.getNotificationImage())
                                .placeholder(R.drawable.img_preview)
                                .error(R.drawable.img_preview)
                                .into(imageView);
                    } else
                        Picasso.get().load(R.drawable.img_preview).error(R.drawable.img_preview).into(imageView);
                }

                @Override
                public void onItemClicked(View view, Object object, int position) {
                }
            });
            mRecyclerView.setAdapter(customListAdapter);
        } else {
            Toast.makeText(this, "No Notification available...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                startActivity(new Intent(MatrimonyNotificationActivity.this, HomeActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
