package in.co.vsys.myssksamaj.matrimonyutils;

import android.content.Context;

import in.co.vsys.myssksamaj.model.Chat;
import in.co.vsys.myssksamaj.model.FirebaseModel;

public class ChatPresenter implements ChatContract.Presenter, ChatContract.OnSendMessageListener,
        ChatContract.OnGetMessagesListener, ChatContract.OnUpdateMessageListener {
    private ChatContract.View mView;
    private ChatInteractor mChatInteractor;

    public ChatPresenter(ChatContract.View view) {
        this.mView = view;
        mChatInteractor = new ChatInteractor(this, this, this);
    }

/*    @Override
    public void sendMessage(Context context, Chat chat, String receiverFirebaseToken) {
        mChatInteractor.sendMessageToFirebaseUser(context, chat, receiverFirebaseToken);
    }*/

    @Override
    public void sendMessage(Context context, Chat chat, String receiverFirebaseToken, int memberId, String receiverTokenId, String sendorImage) {

        mChatInteractor.sendMessageToFirebaseUser(context, chat, receiverFirebaseToken, memberId, receiverTokenId, sendorImage);
    }

    @Override
    public void sendUserDetails(Context context, FirebaseModel user) {

        mChatInteractor.sendUserToFirebase(context, user);

    }

    @Override
    public void getChatList(Context context, String memberId) {

        mChatInteractor.getChatListFromFirebase(context, memberId);

    }

    @Override
    public void getChatList1(Context context, Chat chat, String memberId) {

        mChatInteractor.getChatListFromFirebase1(context, chat, memberId);
    }


    @Override
    public void getMessage(String senderUid, String receiverUid, int memberId) {
        mChatInteractor.getMessageFromFirebaseUser(senderUid, receiverUid, memberId);
    }

    @Override
    public void onSendMessageSuccess() {
        mView.onSendMessageSuccess();
    }

    @Override
    public void onUpdateMessageSuccess() {
        mView.onUpdateMessageSuccess();
    }

    @Override
    public void onSendMessageFailure(String message) {
        mView.onSendMessageFailure(message);
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {
        mView.onGetMessagesSuccess(chat);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        mView.onGetMessagesFailure(message);
    }
}