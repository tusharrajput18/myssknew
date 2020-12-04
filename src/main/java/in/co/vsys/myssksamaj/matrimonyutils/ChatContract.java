package in.co.vsys.myssksamaj.matrimonyutils;

import android.content.Context;

import in.co.vsys.myssksamaj.model.Chat;
import in.co.vsys.myssksamaj.model.FirebaseModel;

public interface ChatContract {
    interface View {
        void onSendMessageSuccess();

        void onUpdateMessageSuccess();

        void onSendMessageFailure(String message);

        void onGetMessagesSuccess(Chat chat);

        void onGetMessagesFailure(String message);
    }

    interface Presenter {
        void sendMessage(Context context, Chat chat, String FirebaseToken, int memberId, String receiverTokenId, String sendorImage);

        void getMessage(String senderUid, String receiverUid, int memberId);

        void sendUserDetails(Context context, FirebaseModel user);

        void getChatList(Context context, String memberId);

        void getChatList1(Context context, Chat chat, String memberId);
    }

    interface Interactor {

        void sendMessageToFirebaseUser(Context context, Chat chat, String FirebaseToken, int memberId, String receiverTokenId, String sendorImage);

        void getMessageFromFirebaseUser(String senderUid, String receiverUid, int memberId);

        void sendUserToFirebase(Context context, FirebaseModel user);

        void getChatListFromFirebase(Context context, String memberId);

        void getChatListFromFirebase1(Context context, Chat chat, String memberId);
    }

    interface OnSendMessageListener {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);
    }

    interface OnUpdateMessageListener {
        void onUpdateMessageSuccess();

    }

    interface OnGetMessagesListener {
        void onGetMessagesSuccess(Chat chat);

        void onGetMessagesFailure(String message);
    }

}
