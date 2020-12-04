package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.BlockContract;
import in.co.vsys.myssksamaj.contracts.UpdateMsgSentToReadContract;
import in.co.vsys.myssksamaj.interactors.CustomChatInteractor;
import in.co.vsys.myssksamaj.matrimonyutils.ChatContract;
import in.co.vsys.myssksamaj.matrimonyutils.ChatPresenter;
import in.co.vsys.myssksamaj.matrimonyutils.Constants;
import in.co.vsys.myssksamaj.model.Chat;
import in.co.vsys.myssksamaj.model.data_models.ChatModel;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.presenters.BlockPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.UpdateMsgSentToReadPresender;
import in.co.vsys.myssksamaj.utils.DateFormatter;
import in.co.vsys.myssksamaj.utils.Utilities;

public class ChatActivity extends AppCompatActivity implements ChatContract.View,
        in.co.vsys.myssksamaj.contracts.ChatContract.ChatView, BlockContract.BlockView, UpdateMsgSentToReadContract.updateMsgSentToReadView {

    private Context mContext;
    private RecyclerView mRecyclerViewChat;
    private EditText edtInput;
    private ChatPresenter mChatPresenter;
    private CustomListAdapter mChatRecyclerAdapter;
    private int memberId;
    private int message_receiverId;
    private SharedPreferences mPreferences;
    private String memberName = "", messageReceiverName, imageUrl, sendorImageUrl;
    private Toolbar mToolbar;
    private CircleImageView mCircleImageView;
    private TextView tvTitle, replySenderName, replyMessage;
    private ProgressBar mProgressBar;
    private String tokenId, receiverTokenId;
    private ImageView imgBack, replyCancel, send, replyMessageImg, deleteMessageImg;
    private List<ChatModel> chatList = new ArrayList<>();
    private LinearLayout blockOverlay;
    private in.co.vsys.myssksamaj.contracts.ChatContract.ChatOps chatPresenter;
    private LinearLayout chatOptionsContainer;
    private RelativeLayout replyContainer;
    private Button unblock;
    private TextView blockText;
    private boolean isBlocked = false;
    private boolean isFirstTimeRendered = false;
    private UpdateMsgSentToReadContract.updateMsgSentToReadOPS mUpdateMsgSentToReadPresenter;

    private ChatModel mCurrentSelectedChat;
    private BlockContract.BlockOps blockPresenter;

    private int visibleItems, visibleItemCount, totalItemCount, page = 0;
    private boolean loading = false;

    private boolean recyclerviewInitialized = false;
    private String isBlockedStr, blockedBy;
    List<ChatModel> arrlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_chat);
        mContext = this;

        mUpdateMsgSentToReadPresenter = new UpdateMsgSentToReadPresender(this);


        mRecyclerViewChat = (RecyclerView) findViewById(R.id.recycler_matrimonyChat);
        mRecyclerViewChat.setNestedScrollingEnabled(true);
        mRecyclerViewChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy >= 0)
                    return;
                int pastVisibleItems = ((LinearLayoutManager) mRecyclerViewChat.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                if (pastVisibleItems == 0 && recyclerviewInitialized) {
                    int current = ((LinearLayoutManager) mRecyclerViewChat.getLayoutManager()).findFirstVisibleItemPosition();

//                    visibleItemCount = mRecyclerViewChat.getLayoutManager().getChildCount();
//                    totalItemCount = mRecyclerViewChat.getLayoutManager().getItemCount();
//
//                    if(totalItemCount - visibleItemCount > 0)
//                        return;
//
//                    visibleItems = ((LinearLayoutManager) mRecyclerViewChat.getLayoutManager()).findFirstVisibleItemPosition();
/*
                    if(current>pastVisibleItems){
                        Toast.makeText(mContext, "true", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, "false", Toast.LENGTH_SHORT).show();
                    }

                    pastVisibleItems=current;*/

                    if (!loading) {
//                        if ((visibleItemCount + visibleItems) >= totalItemCount) {
                        loading = true;
                        page++;
                        reloadChat();
//                        }
                    }
                }
                recyclerviewInitialized = true;
            }
        });

        edtInput = (EditText) findViewById(R.id.edt_matrimonyChat);
        mCircleImageView = (CircleImageView) findViewById(R.id.toolbar_chat_image);
        tvTitle = (TextView) findViewById(R.id.toolbar_chat_name);
        blockText = (TextView) findViewById(R.id.blocked_text);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_matrimonyChat);
        mProgressBar = (ProgressBar) findViewById(R.id.chat_progressBar);
        imgBack = (ImageView) findViewById(R.id.img_chatBack);
        replyCancel = (ImageView) findViewById(R.id.cancel_reply);
        replyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentSelectedChat = null;
                replyContainer.setVisibility(View.GONE);

                if (chatOptionsContainer.getVisibility() == View.VISIBLE) {
                    mCurrentSelectedChat = null;
                    chatOptionsContainer.setVisibility(View.GONE);
                    return;
                }
            }
        });
        send = (ImageView) findViewById(R.id.send);
        unblock = (Button) findViewById(R.id.unblock);
        unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBlocked = false;

                blockOverlay.setVisibility(View.GONE);
                blockPresenter.updateBlockStatus(memberId, message_receiverId, 0);

                edtInput.setEnabled(true);
                edtInput.setClickable(true);
            }
        });

        replySenderName = (TextView) findViewById(R.id.reply_sender_name);
        replyMessage = (TextView) findViewById(R.id.message_to_reply);

        send = (ImageView) findViewById(R.id.send);

        replyMessageImg = (ImageView) findViewById(R.id.reply_message);
        replyMessageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked)
                    return;

                if (mCurrentSelectedChat == null) {
                    chatOptionsContainer.setVisibility(View.GONE);
                    return;
                }
               /* replySenderName.setText(mCurrentSelectedChat.getSenderName());
                replyMessage.setText(mCurrentSelectedChat.getMessage());
                replyContainer.setVisibility(View.VISIBLE);*/

                replySenderName.setText(arrlist.get(0).getSenderName());
                replyMessage.setText(arrlist.get(0).getMessage());
                replyContainer.setVisibility(View.VISIBLE);
            }
        });
        deleteMessageImg = findViewById(R.id.delete_message);
        deleteMessageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked)
                    return;

                if (mCurrentSelectedChat == null) {
                    chatOptionsContainer.setVisibility(View.GONE);
                    return;
                }
                int j = 0;
                while (j < arrlist.size()) {
                    Log.d("mytag", "onClick: " + arrlist.get(j).getChatId());
                    chatPresenter.deleteChat(Integer.parseInt(arrlist.get(j).getChatId()));
                    chatList.remove(arrlist.get(j));
                    mChatRecyclerAdapter.notifyDataSetChanged();
                    j++;
                }

                deleteMessageImg.setVisibility(View.GONE);


               /* chatList.remove(mCurrentSelectedChat);
                mChatRecyclerAdapter.notifyDataSetChanged();
                chatPresenter.deleteChat(Integer.parseInt(mCurrentSelectedChat.getChatId()));
                mCurrentSelectedChat = null;*/

            }
        });
        setSupportActionBar(mToolbar);

        blockOverlay = findViewById(R.id.block_overlay);
        chatOptionsContainer = findViewById(R.id.chat_options_container);
        replyContainer = findViewById(R.id.reply_container);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewChat.setLayoutManager(layoutManager);
        mRecyclerViewChat.setHasFixedSize(true);
        mRecyclerViewChat.setBackgroundResource(R.color.colorHind);
        mRecyclerViewChat.setBackgroundColor(Color.WHITE);

//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);

        mRecyclerViewChat.setLayoutManager(layoutManager);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        memberId = mPreferences.getInt("memberId", 0);
        memberName = mPreferences.getString("nav_memberName", "");
        message_receiverId = mPreferences.getInt("messageReceiverId", 0);
        messageReceiverName = mPreferences.getString("messageReceiverName", "");

        imageUrl = mPreferences.getString("messageReceiverImage", "");

        sendorImageUrl = mPreferences.getString("nav_profileUrl", "");

        tokenId = mPreferences.getString(Constants.ARG_FIREBASE_TOKEN, "");

        receiverTokenId = mPreferences.getString("receiverTokenId", "");

//        mChatPresenter = new ChatPresenter(this);
        String strSenderId = String.valueOf(message_receiverId);
        String strReciverID = String.valueOf(MainPresenter.getInstance().getMemberId(mContext));
        mUpdateMsgSentToReadPresenter.updateMsgSentToRead(strSenderId, strReciverID);

        chatPresenter = new in.co.vsys.myssksamaj.presenters.ChatPresenter(this);
        reloadChat();
        init();

        blockPresenter = new BlockPresenter(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked)
                    return;

                String message = edtInput.getText().toString().trim();
                if (Utilities.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked)
                    return;
                onBackPressed();
            }
        });

        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBlocked)
                    return;

                Intent intent = new Intent(ChatActivity.this, MatriChatViewProfileActivity.class);
                intent.putExtra("chatMemberId", message_receiverId);
                startActivity(intent);
            }
        });

        readIntent();
        checkIsBlocked();
    }

    private void readIntent() {
        if (!Utilities.isIntentPresent(getIntent()))
            return;

        Bundle bundle = getIntent().getExtras();
        isBlockedStr = bundle.getString(Constants.ShareableIntents.IS_BLOCKED);
        blockedBy = bundle.getString(Constants.ShareableIntents.BLOCKED_BY);
    }

    private void checkIsBlocked() {
        if (Utilities.isEmpty(isBlockedStr) || isBlockedStr.equalsIgnoreCase("0"))
            return;

        isBlocked = true;
        blockOverlay.setVisibility(View.VISIBLE);

        edtInput.setEnabled(false);
        edtInput.setClickable(false);

        if (blockedBy.equalsIgnoreCase("" + memberId)) {
            blockText.setText(getString(R.string.you_blocked_text));
            unblock.setVisibility(View.VISIBLE);

        } else {
            blockText.setText(getString(R.string.you_are_blocked_text));
            unblock.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomChatInteractor.getInstance().setChatView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CustomChatInteractor.getInstance().setChatView(null);
    }

    private void blockDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Do you want to block " + messageReceiverName + "?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                isBlocked = true;
                blockPresenter.updateBlockStatus(memberId, message_receiverId, 1);
                blockOverlay.setVisibility(View.VISIBLE);

                edtInput.setEnabled(false);
                edtInput.setClickable(false);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.block:
                if (isBlocked)
                    return true;

                blockDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
//        mChatPresenter.getMessage(String.valueOf(memberId), String.valueOf(message_receiverId), memberId);
        tvTitle.setText("" + messageReceiverName);
        String chatImage = "";


        if (!imageUrl.equals("") && imageUrl.length() > 10) {
            Picasso.get().load(imageUrl)
                    .placeholder(R.drawable.img_preview)
                    .into(mCircleImageView);
        } else {
            Picasso.get().load(R.drawable.img_preview)
                    .into(mCircleImageView);
        }
    }

    private void sendMessage(String message) {

        String receiver = "" + messageReceiverName;
        String receiverUid = String.valueOf(message_receiverId);
        String sender = "" + memberName;
        String senderUid = "" + memberId;
        //  String receiverFirebaseToken = getArguments().getString(Constants.ARG_FIREBASE_TOKEN);
       /* Chat chat = new Chat(
                1, sender, // Type for text messages
                receiver,
                senderUid,
                receiverUid,
                message,
                System.currentTimeMillis(),
                false);*/
        Chat chat = new Chat(
                1, sender, // Type for text messages
                receiver,
                senderUid,
                receiverUid,
                message,
                false,
                sendorImageUrl);

        ChatModel chatModel = new ChatModel();
        String date = DateFormatter.formatDate(Calendar.getInstance(), DateFormatter.CHAT_DATE_TIME);
        chatModel.setDate1(date);
        chatModel.setMessage(message);
        int parentId = 0;
        if (mCurrentSelectedChat != null) {
            parentId = Integer.parseInt(mCurrentSelectedChat.getChatId());
            chatModel.setParentId("" + parentId);
            chatModel.setReply_msg("" + mCurrentSelectedChat.getMessage());

            chatModel.setReply_senderId("" + mCurrentSelectedChat.getSenderId());
            chatModel.setReply_senderName("" + mCurrentSelectedChat.getSenderName());
            chatModel.setReply_ReceiverId(mCurrentSelectedChat.getReceiverId());
            chatModel.setReply_ReceiverName("" + mCurrentSelectedChat.getReceiverName());
        }

        chatModel.setSenderId(Utilities.getString(memberId));
        chatModel.setReceiverId(Utilities.getString(message_receiverId));

        chatList.add(chatModel);

        mRecyclerViewChat.scrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);

//        mChatPresenter.sendMessage(this, chat, tokenId, memberId, receiverTokenId, sendorImageUrl);

        chatPresenter.insertChat(memberId, 1, message_receiverId,
                1, message, parentId, 1);

        Log.d("mytag", "sendMessage: "+memberId+" "+message_receiverId+" "+message+" "+parentId);
        refreshList();
        edtInput.setText("");

        if (mCurrentSelectedChat != null) {
            mCurrentSelectedChat = null;
            chatOptionsContainer.setVisibility(View.GONE);
            replyContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSendMessageSuccess() {

        edtInput.setText("");
    }

    @Override
    public void onSendMessageFailure(String message) {
        edtInput.setText("Message not send");
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateMessageSuccess() {

        mChatRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMessagesSuccess(final Chat chat) {
//        mProgressBar.setVisibility(View.GONE);
//
//        if (Utilities.isEmpty(chat.getMessage()))
//            return;
//
//        chatList.add(chat);
//        if (mChatRecyclerAdapter == null) {
//            mChatRecyclerAdapter = new CustomListAdapter(mContext, mRecyclerViewChat, chatList,
//                    R.layout.item_message_sent, new CustomListAdapter.ItemClickedListener() {
//                @Override
//                public void onViewBound(View itemView, Object object, int position) {
//                    Chat chat1 = (Chat) object;
//
//                    LinearLayout receivedMsgContainer = itemView.findViewById(R.id.msg_received_container);
//                    LinearLayout sentMsgContainer = itemView.findViewById(R.id.msg_sent_container);
//
//                    TextView sentMessageText = (TextView) itemView.findViewById(R.id.send_message_body);
//                    TextView sentMessageTimeText = (TextView) itemView.findViewById(R.id.send_message_time);
//
//                    TextView receivedMessageText = (TextView) itemView.findViewById(R.id.received_message_body);
//                    TextView receivedTimeText = (TextView) itemView.findViewById(R.id.received_message_time);
//
//                    int senderId = Utilities.getInt(chat1.getSenderUid());
//                    int memberId = MainPresenter.getInstance().getMemberId(mContext);
//
//                    if (memberId == senderId) {
//                        sentMsgContainer.setVisibility(View.VISIBLE);
//                        receivedMsgContainer.setVisibility(View.GONE);
//                        Utilities.setText(sentMessageText, chat1.getMessage());
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(chat1.getTimestamp());
//                        Utilities.setText(sentMessageTimeText, DateFormatter.formatDate(calendar,
//                                "dd MMM (hh:mm a)"));
//                    } else {
//                        receivedMsgContainer.setVisibility(View.VISIBLE);
//                        sentMsgContainer.setVisibility(View.GONE);
//                        Utilities.setText(receivedMessageText, chat1.getMessage());
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(chat1.getTimestamp());
//                        Utilities.setText(receivedTimeText, DateFormatter.formatDate(calendar,
//                                "dd MMM (hh:mm a)"));
//                    }
//                }
//
//                @Override
//                public void onItemClicked(View view, Object object, int position) {
//
//                }
//            });
//            mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
//        } else
//            mChatRecyclerAdapter.notifyDataSetChanged();
//
//        mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
    }

    @Override
    public void onGetMessagesFailure(String message) {

    }

    @Override
    public void reloadRecentChat() {
        Log.d("mytag", "reloadRecentChat: hi");
        try {
            if (chatPresenter == null)
                return;

            page = 0;
            updateMsgSentToRead();
            chatPresenter.getRecentChat(Utilities.getString(memberId), Utilities.getString(message_receiverId),
                    Utilities.getString(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMsgSentToRead() {
        mUpdateMsgSentToReadPresenter.updateMsgSentToRead(Utilities.getString(message_receiverId), String.valueOf(MainPresenter.getInstance().getMemberId(mContext)));
    }

    public void reloadChat() {
        try {
            if (chatPresenter == null)
                return;

            chatPresenter.getChat(Utilities.getString(memberId), Utilities.getString(message_receiverId),
                    Utilities.getString(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showChatInserted(String message) {
        chatPresenter.getChat(Utilities.getString(memberId), Utilities.getString(message_receiverId),
                Utilities.getString(page));
    }

    @Override
    public void showChatDeleted(String message) {

    }

    @Override
    public void showChat(List<ChatModel> chatModels) {
        try {
            loading = false;
//        if (Utilities.isListEmpty(chatList))
//            Collections.reverse(chatModels);

            mProgressBar.setVisibility(View.GONE);

            if (Utilities.isListEmpty(chatModels))
                return;
            if (!Utilities.isListEmpty(chatList)) {
                chatList.clear();
            }

            for (ChatModel chatModel : chatModels) {
                chatList.add(0, chatModel);
            }

            refreshList();
            if (!isFirstTimeRendered) {
                mRecyclerViewChat.scrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
                isFirstTimeRendered = true;
            } else {
                if (chatList.size() <= 15)
                    return;

                mRecyclerViewChat.scrollToPosition(15);
                mRecyclerViewChat.scrollBy(0, -100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showRecentChat(List<ChatModel> chatModels) {

//        int j = 0;
//        for (int i = chatList.size() - 10; i < chatList.size(); i++) {
//            if (chatModels.get(j).getMessage().equalsIgnoreCase(chatList.))
//        }
        chatList.clear();
        for (ChatModel chatModel : chatModels) {
            chatList.add(0, chatModel);
        }
        mChatRecyclerAdapter.notifyDataSetChanged();
        mRecyclerViewChat.scrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
    }

    private void refreshList() {
        final String select="0";

        try {
            if (mChatRecyclerAdapter == null) {
                mChatRecyclerAdapter = new CustomListAdapter(mContext, mRecyclerViewChat, chatList,
                        R.layout.item_message_sent, new CustomListAdapter.ItemClickedListener() {
                    @Override
                    public void onViewBound(final View itemView, Object object, final int position) {
                        final ChatModel chatModel = (ChatModel) object;

                        LinearLayout receivedMsgContainer = itemView.findViewById(R.id.msg_received_container);
                        LinearLayout sentMsgContainer = itemView.findViewById(R.id.msg_sent_container);

                        LinearLayout replyReceivedMsgContainer = itemView.findViewById(R.id.reply_received_msg_container);
                        LinearLayout replySentMsgContainer = itemView.findViewById(R.id.reply_sent_msg_container);

                        TextView sentMessageText = (TextView) itemView.findViewById(R.id.send_message_body);
                        TextView sentMessageTimeText = (TextView) itemView.findViewById(R.id.send_message_time);

                        TextView receivedMessageText = (TextView) itemView.findViewById(R.id.received_message_body);
                        TextView receivedTimeText = (TextView) itemView.findViewById(R.id.received_message_time);

                        ImageView img_status_send = itemView.findViewById(R.id.img_message_send);
                        ImageView img_status_recive = itemView.findViewById(R.id.img_message_status_deliverd);
                        ImageView img_status_seen = itemView.findViewById(R.id.img_message_status_seen);

                        TextView reply_receiver_name = (TextView) itemView.findViewById(R.id.reply_receiver_name);
                        TextView reply_receiver_message_to_reply = (TextView) itemView.findViewById(R.id.reply_receiver_message_to_reply);
                        TextView reply_sender_name = (TextView) itemView.findViewById(R.id.reply_sender_name);
                        TextView reply_msg = (TextView) itemView.findViewById(R.id.reply_msg);

                        int senderId = Utilities.getInt(chatModel.getSenderId());
                        final int memberId = MainPresenter.getInstance().getMemberId(mContext);

                        if (Utilities.isEmpty(chatModel.getMsgReadStatus()) || chatModel.getMsgReadStatus().equals("1")) {
                            img_status_send.setVisibility(View.VISIBLE);
                            img_status_recive.setVisibility(View.GONE);
                            img_status_seen.setVisibility(View.GONE);
                        }
                        if (chatModel.getMsgReadStatus().equals("3")) {
                            img_status_send.setVisibility(View.GONE);
                            img_status_recive.setVisibility(View.VISIBLE);
                            img_status_seen.setVisibility(View.GONE);
                        }
                        if (chatModel.getMsgReadStatus().equals("4")) {
                            img_status_send.setVisibility(View.GONE);
                            img_status_recive.setVisibility(View.GONE);
                            img_status_seen.setVisibility(View.VISIBLE);
                        }

                        itemView.setBackgroundColor(chatModel.isSelected() ? Color.CYAN : Color.WHITE);

                        itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                if (isBlocked)
                                    return true;
                                return true;
                            }
                        });

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mCurrentSelectedChat = chatModel;
                                mCurrentSelectedChat.setSelected(!mCurrentSelectedChat.isSelected());
                                itemView.setBackgroundColor(mCurrentSelectedChat.isSelected() ? getResources().getColor(R.color.light_blue): Color.WHITE);

                                if (mCurrentSelectedChat.isSelected()) {

                                    arrlist.add(mCurrentSelectedChat);
                                } else {
                                    arrlist.remove(mCurrentSelectedChat);
                                }

                                if(arrlist.size()==1){
                                    replyMessageImg.setVisibility(View.VISIBLE);

                                }else {
                                    replyMessageImg.setVisibility(View.GONE);
                                }

                                Log.d("mytag", "arraylist size " + arrlist.size());


                                //if (!mCurrentSelectedChat.getSenderId().equalsIgnoreCase("" + memberId)) {
                                    if(arrlist.size()>=1){
                                        deleteMessageImg.setVisibility(View.VISIBLE);
                                    }else {
                                        deleteMessageImg.setVisibility(View.GONE);
                                    }
                                /*} else
                                    deleteMessageImg.setVisibility(View.GONE);*/
                                //chatOptionsContainer.setVisibility(View.VISIBLE);

                            }
                        });

                        replyReceivedMsgContainer.setVisibility(View.GONE);
                        replySentMsgContainer.setVisibility(View.GONE);
                        receivedMsgContainer.setVisibility(View.GONE);
                        sentMsgContainer.setVisibility(View.GONE);

                        // sent message
                        if (memberId == senderId) {
                            sentMsgContainer.setVisibility(View.VISIBLE);
                            Utilities.setText(sentMessageText, chatModel.getMessage());
//                            String date = DateFormatter.formatDate(chatModel.getDate1(),
//                                    DateFormatter.DB_DATE_TIME_EZACUS, DateFormatter.CHAT_DATE_TIME);
                            Utilities.setText(sentMessageTimeText, chatModel.getDate1());

                            if (Utilities.isEmpty(chatModel.getReply_senderId()) || Utilities.isEmpty(chatModel.getReply_ReceiverId()))
                                return;

                            replySentMsgContainer.setVisibility(View.VISIBLE);

                            if (chatModel.getReply_senderId().equalsIgnoreCase("" + memberId)) {
                                Utilities.setText(reply_sender_name, chatModel.getReply_senderName());
                                Utilities.setText(reply_msg, chatModel.getReply_msg());
                            } else if (chatModel.getReply_ReceiverId().equalsIgnoreCase("" + memberId)) {
                                Utilities.setText(reply_sender_name, chatModel.getReply_senderName());
                                Utilities.setText(reply_msg, chatModel.getReply_msg());
                            }
                        }
                        // received message
                        else {
                            receivedMsgContainer.setVisibility(View.VISIBLE);
                            Utilities.setText(receivedMessageText, chatModel.getMessage());
//                            String date = DateFormatter.formatDate(chatModel.getDate1(),
//                                    DateFormatter.DB_DATE_TIME_EZACUS, DateFormatter.CHAT_DATE_TIME);
                            Utilities.setText(receivedTimeText, chatModel.getDate1());

                            if (Utilities.isEmpty(chatModel.getReply_senderId()) || Utilities.isEmpty(chatModel.getReply_ReceiverId()))
                                return;

                            replyReceivedMsgContainer.setVisibility(View.VISIBLE);

                            if (chatModel.getReply_senderId().equalsIgnoreCase("" + memberId)) {
                                Utilities.setText(reply_receiver_name, chatModel.getReply_senderName());
                                Utilities.setText(reply_receiver_message_to_reply, chatModel.getReply_msg());
                            } else if (chatModel.getReply_ReceiverId().equalsIgnoreCase("" + memberId)) {
                                Utilities.setText(reply_receiver_name, chatModel.getReply_senderName());
                                Utilities.setText(reply_receiver_message_to_reply, chatModel.getReply_msg());
                            }
                        }
                    }

                    @Override
                    public void onItemClicked(View view, Object object, int position) {

                    }
                });
                mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
            } else
                mChatRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getChatId() {
        return "" + message_receiverId;
    }

    @Override
    public void onBackPressed() {
        if (chatOptionsContainer.getVisibility() == View.VISIBLE) {
            mCurrentSelectedChat = null;
            chatOptionsContainer.setVisibility(View.GONE);
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public void showLoading() {
        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(mContext);
        }

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mProgressBar == null) {
            return;
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showBlockedStatus(String message) {

    }

    @Override
    public void showResponse(Entity entity) {
        refreshList();
    }
}