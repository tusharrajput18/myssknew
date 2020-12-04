package in.co.vsys.myssksamaj.matrimonyutils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.co.vsys.myssksamaj.model.Chat;
import in.co.vsys.myssksamaj.model.FirebaseModel;

public class ChatInteractor implements ChatContract.Interactor {
    private static final String TAG = "ChatInteractor";

    private ChatContract.OnSendMessageListener mOnSendMessageListener;
    private ChatContract.OnGetMessagesListener mOnGetMessagesListener;
    private ChatContract.OnUpdateMessageListener mOnUpdateMessagesListener;


    public ChatInteractor(ChatContract.OnSendMessageListener onSendMessageListener) {
        this.mOnSendMessageListener = onSendMessageListener;
    }

    public ChatInteractor(ChatContract.OnGetMessagesListener onGetMessagesListener) {
        this.mOnGetMessagesListener = onGetMessagesListener;
    }


    public ChatInteractor(ChatContract.OnSendMessageListener onSendMessageListener,
                          ChatContract.OnGetMessagesListener onGetMessagesListener, ChatContract.OnUpdateMessageListener onUpdateMessageListener) {
        this.mOnSendMessageListener = onSendMessageListener;
        this.mOnGetMessagesListener = onGetMessagesListener;
        this.mOnUpdateMessagesListener = onUpdateMessageListener;
    }

    @Override
    public void sendMessageToFirebaseUser(final Context context, final Chat chat, final String FirebaseToken, int memberId, String receiverTokenId,String sendorImage) {


        String senderId = chat.getSenderUid();
        String receiverId = chat.getReceiverUid();


        if (senderId.equals(String.valueOf(memberId))) {

            DatabaseReference senderChat = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(senderId).child(Constants.ARG_CHAT_ROOMS);
            DatabaseReference receiverChat = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(receiverId).child(Constants.ARG_CHAT_ROOMS);

            DatabaseReference senderChatList = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(senderId).child(Constants.ARG_CHAT_LIST);
            DatabaseReference receiverChatList = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(receiverId).child(Constants.ARG_CHAT_LIST);

            senderChat.child(receiverId).child(String.valueOf(chat.getTimestamp())).setValue(chat);
            receiverChat.child(senderId).child(String.valueOf(chat.getTimestamp())).setValue(chat);

            senderChatList.child(receiverId).setValue(chat);
            receiverChatList.child(senderId).setValue(chat);

        } else {

            DatabaseReference senderChat = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(senderId).child(Constants.ARG_CHAT_ROOMS);
            DatabaseReference receiverChat = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(receiverId).child(Constants.ARG_CHAT_ROOMS);

            DatabaseReference senderChatList = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(senderId).child(Constants.ARG_CHAT_LIST);
            DatabaseReference receiverChatList = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(receiverId).child(Constants.ARG_CHAT_LIST);

            senderChat.child(senderId).child(String.valueOf(chat.getTimestamp())).setValue(chat);
            receiverChat.child(receiverId).child(String.valueOf(chat.getTimestamp())).setValue(chat);

            senderChatList.child(senderId).setValue(chat);
            receiverChatList.child(receiverId).setValue(chat);

        }

        if (chat.getType() == 1) {

            sendPushNotificationToReceiver(
                    chat.getSender(),
                    sendorImage,
                    chat.getMessage(),
                    chat.getSenderUid(),
                    FirebaseToken,
                    receiverTokenId);


        }


        //getMessageFromFirebaseUser(chat.getReporterUid(), chat.getReportedUid());

        // send push notification to the receiver

        /*if (chat.getType() == 1){

            sendPushNotificationToReceiver(
                    chat.getSender(),
                    chat.getMessage(),
                    chat.getSenderUid(),
                    new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
                    receiverFirebaseToken);


        } else if (chat.getType() == 2){

            sendPushNotificationToReceiver(
                    chat.getSender(),
                    "sent you an audio",
                    chat.getSenderUid(),
                    new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
                    receiverFirebaseToken);

        } else if (chat.getType() == 3){

            sendPushNotificationToReceiver(
                    chat.getSender(),
                    "sent you an image",
                    chat.getSenderUid(),
                    new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
                    receiverFirebaseToken);

        }*/


        mOnSendMessageListener.onSendMessageSuccess();

    }

    private void sendPushNotificationToReceiver(String username, String imageUrl, String message, String uid, String firebaseToken, String receiverFirebaseToken) {

        // Check and sent push only if tokens are different
        if (!firebaseToken.equals(receiverFirebaseToken)) {

            FcmNotificationBuilder.initialize()
                    .title(username)
                    .message(message)
                    .username(imageUrl)
                    .uid(uid)
                    .firebaseToken(firebaseToken)
                    .receiverFirebaseToken(receiverFirebaseToken)
                    .send();
        }

    }
   /* private void sendPushNotificationToReceiver(String username, String message, String uid, String firebaseToken, String receiverFirebaseToken) {

        // Check and sent push only if tokens are different
        if (!firebaseToken.equals(receiverFirebaseToken)){

            FcmNotificationBuilder.initialize()
                    .title(username)
                    .message(message)
                    .username(username)
                    .uid(uid)
                    .firebaseToken(firebaseToken)
                    .receiverFirebaseToken(receiverFirebaseToken)
                    .send();
        }

    }*/

    @Override
    public void sendUserToFirebase(Context context, FirebaseModel user) {

        String memberId = user.getUid();

        if (!memberId.equals("")) {

            DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS);
            userDatabase.child(memberId).child("UserDetails").setValue(user);

            mOnSendMessageListener.onSendMessageSuccess();
        }
    }

    @Override
    public void getChatListFromFirebase(Context context, final String memberId) {


        if (memberId != null) {

            final DatabaseReference userDatabase;

            userDatabase = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS);

            userDatabase.child(memberId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.e(TAG, "list====: " + dataSnapshot.getValue());

                    if (dataSnapshot.hasChild(Constants.ARG_CHAT_LIST)) {

                        userDatabase.child(memberId).child(Constants.ARG_CHAT_LIST).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Log.e(TAG, "value " + dataSnapshot.getValue());

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if (snapshot.exists()) {

                                        Log.e(TAG, "snapshot==========: " + snapshot.getValue());

                                        Chat chat = snapshot.getValue(Chat.class);

                                        mOnGetMessagesListener.onGetMessagesSuccess(chat);
                                    }

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Log.d(TAG, "onDataChange: ");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void getChatListFromFirebase1(Context context, Chat chat, final String memberId) {


        if (chat.getSenderUid().equals(memberId)) {

            /*DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS);
            userDatabase.child(memberId).child();*/

            final DatabaseReference userDatabase;

            userDatabase = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS);

            userDatabase.child(chat.getReceiverUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.e(TAG, "list====: " + dataSnapshot.getValue());

                    if (dataSnapshot.hasChild(Constants.ARG_CHAT_LIST)) {

                        userDatabase.child(memberId).child(Constants.ARG_CHAT_LIST).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Log.e(TAG, "value " + dataSnapshot.getValue());

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if (snapshot.exists()) {

                                        Log.e(TAG, "snapshot==========: " + snapshot.getValue());

                                        Chat chat = snapshot.getValue(Chat.class);

                                        mOnGetMessagesListener.onGetMessagesSuccess(chat);
                                    }

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Log.d(TAG, "onDataChange: ");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (chat.getReceiverUid().equals(memberId)) {

            final DatabaseReference userDatabase;

            userDatabase = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS);

            userDatabase.child(chat.getSenderUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.e(TAG, "list====: " + dataSnapshot.getValue());

                    if (dataSnapshot.hasChild(Constants.ARG_CHAT_LIST)) {

                        userDatabase.child(memberId).child(Constants.ARG_CHAT_LIST).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Log.e(TAG, "value " + dataSnapshot.getValue());

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if (snapshot.exists()) {

                                        Log.e(TAG, "snapshot==========: " + snapshot.getValue());

                                        Chat chat = snapshot.getValue(Chat.class);

                                        mOnGetMessagesListener.onGetMessagesSuccess(chat);
                                    }

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Log.d(TAG, "onDataChange: ");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void getMessageFromFirebaseUser(String senderUid, String receiverUid, int memberId) {


        if (senderUid.equals(String.valueOf(memberId))) {

            DatabaseReference senderChat = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(senderUid).child(Constants.ARG_CHAT_ROOMS);

            senderChat.child(receiverUid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.exists()) {

                        Log.d(TAG, "onChildAdded: " + dataSnapshot);

                        Chat chat = dataSnapshot.getValue(Chat.class);

                        mOnGetMessagesListener.onGetMessagesSuccess(chat);
                    }


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                    mOnUpdateMessagesListener.onUpdateMessageSuccess();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {


                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                }
            });

        } else {

            final DatabaseReference receiverChat = FirebaseDatabase.getInstance().getReference(Constants.ARG_USERS).child(receiverUid).child(Constants.ARG_CHAT_ROOMS);

            receiverChat.child(senderUid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Chat chat = dataSnapshot.getValue(Chat.class);

                    mOnGetMessagesListener.onGetMessagesSuccess(chat);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                    mOnUpdateMessagesListener.onUpdateMessageSuccess();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {


                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                }
            });

        }

    }
}
