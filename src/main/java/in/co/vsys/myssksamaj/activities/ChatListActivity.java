package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.ChatThreadContract;
import in.co.vsys.myssksamaj.matrimonyutils.Constants;
import in.co.vsys.myssksamaj.model.Chat;
import in.co.vsys.myssksamaj.model.FirebaseModel;
import in.co.vsys.myssksamaj.model.ListChatViewHolder;
import in.co.vsys.myssksamaj.model.data_models.ChatThreadModel;
import in.co.vsys.myssksamaj.presenters.ChatThreadPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

public class ChatListActivity extends AppCompatActivity implements ChatThreadContract.ChatThreadView {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private int memberId;
    private SharedPreferences mPreferences;
    private RecyclerView.Adapter<ListChatViewHolder> mAdapter;
    private static final String TAG = ChatListActivity.class.getSimpleName();
    private ChatThreadContract.ChatThreadOps chatThreadPresenter;
    private List<ChatThreadModel> mChatThreadModels = new ArrayList<>();
    private CustomListAdapter chatThreadAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_chat_list);
        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar_matrimonyChat_list);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Chat");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChatThreads();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_matrimonyChatList);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_matriChatList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setBackgroundResource(R.color.colorHind);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreferences.getInt("memberId", 0);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

       /* mChatPresenter = new ChatPresenter(this);
        mChatPresenter.getChatList(mContext, String.valueOf(memberId));*/
//        initiate();
//        mRecyclerView.setAdapter(mAdapter);

        chatThreadPresenter = new ChatThreadPresenter(this);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        startActivity(new Intent(mContext, HomeActivity.class));
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(mContext, HomeActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initiate() {

        DatabaseReference visitors = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(String.valueOf(memberId)).child(Constants.ARG_CHAT_LIST);

        visitors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    // Toast.makeText(mContext, "chat list found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "list not available...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query = visitors.orderByChild("timestamp");

        mAdapter = getFirebaseRecyclerAdapter(query);

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });
    }

    private FirebaseRecyclerAdapter<Chat, ListChatViewHolder> getFirebaseRecyclerAdapter(Query query) {

        return new FirebaseRecyclerAdapter<Chat, ListChatViewHolder>(Chat.class, R.layout.row_matrimony_chat_list, ListChatViewHolder.class, query) {
            @Override
            protected void populateViewHolder(ListChatViewHolder viewHolder, final Chat model, final int position) {

                setuppost(viewHolder, model, position, null);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String receiverId = model.getReceiverUid();
                        String sendorId = model.getSenderUid();

                        if (receiverId.equals(String.valueOf(memberId))) {


                            Log.e(TAG, "chat id ==============: " + sendorId);

                            String name = model.getSender();
                            String imageUrl = model.getImageUrl();

                            mPreferences.edit().putInt("messageReceiverId", Integer.parseInt(sendorId)).apply();
                            mPreferences.edit().putString("messageReceiverName", name).apply();
                            mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();

                            startActivity(new Intent(mContext, ChatActivity.class));
                        } else {

                            Log.e(TAG, "chat id ==============: " + receiverId);

                            String name = model.getReceiver();
                            String imageUrl = model.getImageUrl();

                            mPreferences.edit().putInt("messageReceiverId", Integer.parseInt(receiverId)).apply();
                            mPreferences.edit().putString("messageReceiverName", name).apply();
                            mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();

                            Log.e("url", name);

                            startActivity(new Intent(mContext, ChatActivity.class));
                        }
                    }
                });
            }

            /*@Override
            public ListChatViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

                final ListChatViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                viewHolder.setOnClickListener(new ListChatViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                    }
                });

                return viewHolder;
            }*/

            @Override
            public void onViewRecycled(@NonNull ListChatViewHolder holder) {
                super.onViewRecycled(holder);
            }
        };
    }

    private void setuppost(final ListChatViewHolder chatViewHolder, final Chat chat, final int position, final String inPostKey) {

        if (chat.getSenderUid().equals(String.valueOf(memberId))) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).child(chat.getReceiverUid()).child("UserDetails");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    FirebaseModel firebaseModel = dataSnapshot.getValue(FirebaseModel.class);
                    assert firebaseModel != null;

                    try {
                        if (!TextUtils.isEmpty(firebaseModel.getUid()) && firebaseModel.getUid().length() > 0) {
                            chatViewHolder.setUserId(firebaseModel.getUid());
                        }

                        if (!TextUtils.isEmpty(firebaseModel.getPhotoUrl()) && firebaseModel.getPhotoUrl().length() > 10) {
                            chatViewHolder.setPhotoImage(firebaseModel.getPhotoUrl());
                        }

                        if (firebaseModel.getName() != null) {
                            chatViewHolder.setUserName(firebaseModel.getName());
                        }

                        if (firebaseModel.getPhotoUrl() != null) {
                            chatViewHolder.setPhotoImage(firebaseModel.getPhotoUrl());
                        }

                        if (chat.getType() == 1) {
                            chatViewHolder.setLastMessage(chat.getMessage());
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (chat.getReceiverUid().equals(String.valueOf(memberId))) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).child(chat.getSenderUid()).child("UserDetails");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    try {
                        FirebaseModel firebaseModel = dataSnapshot.getValue(FirebaseModel.class);

                        assert firebaseModel != null;

                        if (!Utilities.isEmpty(firebaseModel.getUid()) && firebaseModel.getUid().length() > 0) {
                            chatViewHolder.setUserId(firebaseModel.getUid());
                        }

                        if (firebaseModel.getPhotoUrl() != null) {
                            chatViewHolder.setPhotoImage(firebaseModel.getPhotoUrl());
                        }

                        if (firebaseModel.getName() != null) {
                            chatViewHolder.setUserName(firebaseModel.getName());
                        }

                        if (firebaseModel.getPhotoUrl() != null) {
                            chatViewHolder.setPhotoImage(firebaseModel.getPhotoUrl());
                        }

                        if (chat.getType() == 1) {
                            chatViewHolder.setLastMessage(chat.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void showChatThreads(List<ChatThreadModel> chatThreadModels) {

        Log.d(TAG, "showChatThreads: "+chatThreadModels.size());
        mChatThreadModels.clear();

        mChatThreadModels.addAll(chatThreadModels);
        Collections.reverse(mChatThreadModels);
        chatThreadAdapter = new CustomListAdapter(mContext, mRecyclerView, mChatThreadModels, R.layout.row_matrimony_chat_list,
                new CustomListAdapter.ItemClickedListener() {
                    @Override
                    public void onViewBound(View view, Object object, int position) {
                        final ChatThreadModel chatThreadModel = (ChatThreadModel) object;
                        String image;

                        if (chatThreadModel.getSenderId().equalsIgnoreCase("" + memberId)) {
                            image = chatThreadModel.getReceiverPhoto();
                        } else
                            image = chatThreadModel.getSenderPhoto();

                        LinearLayout layout_rowChatList=view.findViewById(R.id.layout_rowChatList);


                        layout_rowChatList.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {

                                Toast.makeText(ChatListActivity.this, ""+chatThreadModel.getChatThreadId()+" "+chatThreadModel.getSenderName(), Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });

                        CircleImageView profileImage = view.findViewById(R.id.row_chatListImage);
                        ImageView blockImg = view.findViewById(R.id.block);
                        TextView row_chatListLastMessage = view.findViewById(R.id.row_chatListLastMessage);
                        TextView row_chatListName = view.findViewById(R.id.row_chatListName);
                        TextView tv_unreadcount = view.findViewById(R.id.tv_unreadcount);

                        if(chatThreadModel.getUnreadMsgCount().equalsIgnoreCase("0")){
                            tv_unreadcount.setVisibility(View.GONE);
                        }else {
                            tv_unreadcount.setVisibility(View.VISIBLE);
                            tv_unreadcount.setText(chatThreadModel.getUnreadMsgCount());
                        }

                        String otherPersonName = "";
                        if (chatThreadModel.getSenderId().equalsIgnoreCase("" + memberId)) {
                            otherPersonName = chatThreadModel.getReceiverName();
                        } else
                            otherPersonName = chatThreadModel.getSenderName();

                        Utilities.setText(row_chatListName, otherPersonName);
                        Utilities.setText(row_chatListLastMessage, chatThreadModel.getMessage());

                        if (!TextUtils.isEmpty(image) && image.length() > 10) {

                            Picasso.get()
                                    .load(image)
                                    .placeholder(R.drawable.img_preview)
                                    .error(R.drawable.img_preview)
                                    .into(profileImage);
                        } else {

                            Picasso.get()
                                    .load(R.drawable.img_preview)
                                    .placeholder(R.drawable.img_preview)
                                    .into(profileImage);
                        }

                        if (chatThreadModel.getIsBlocked() == 1) {
                            blockImg.setVisibility(View.VISIBLE);
                        } else
                            blockImg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onItemClicked(View view, Object object, int position) {
                        ChatThreadModel chatThreadModel = (ChatThreadModel) object;

                        String receiverId = chatThreadModel.getReceiverId();
                        String sendorId = chatThreadModel.getSenderId();

                        if (receiverId.equals(String.valueOf(memberId))) {


                            Log.e(TAG, "chat id ==============: " + sendorId);

                            String name = chatThreadModel.getSenderName();
                            String image;

                            if (chatThreadModel.getSenderId().equalsIgnoreCase("" + memberId)) {
                                image = chatThreadModel.getReceiverPhoto();
                            } else
                                image = chatThreadModel.getSenderPhoto();


                            mPreferences.edit().putInt("messageReceiverId", Integer.parseInt(sendorId)).apply();
                            mPreferences.edit().putString("messageReceiverName", name).apply();
                            mPreferences.edit().putString("messageReceiverImage", image).apply();

                            Intent intent = new Intent(mContext, ChatActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.ShareableIntents.IS_BLOCKED, "" + chatThreadModel.getIsBlocked());
                            bundle.putString(Constants.ShareableIntents.BLOCKED_BY, chatThreadModel.getBlockedBy());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {

                            String image, name;

                            if (chatThreadModel.getSenderId().equalsIgnoreCase("" + memberId)) {
                                image = chatThreadModel.getReceiverPhoto();
                                name = chatThreadModel.getReceiverName();
                            } else {
                                image = chatThreadModel.getSenderPhoto();
                                name = chatThreadModel.getSenderName();
                            }

                            mPreferences.edit().putInt("messageReceiverId", Integer.parseInt(receiverId)).apply();
                            mPreferences.edit().putString("messageReceiverName", name).apply();
                            mPreferences.edit().putString("messageReceiverImage", image).apply();

                            Log.e("url", name);

                            Intent intent = new Intent(mContext, ChatActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.ShareableIntents.IS_BLOCKED, "" + chatThreadModel.getIsBlocked());
                            bundle.putString(Constants.ShareableIntents.BLOCKED_BY, chatThreadModel.getBlockedBy());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
        mRecyclerView.setAdapter(chatThreadAdapter);
    }

    private void getChatThreads() {
        chatThreadPresenter.getMyChatThreads("" + memberId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChatThreads();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
    }
}