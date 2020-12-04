package in.co.vsys.myssksamaj.interactors;

import in.co.vsys.myssksamaj.contracts.ChatContract;

/**
 * @author abhijeetjadhav
 */
public class CustomChatInteractor {

    private static CustomChatInteractor instance;
    private ChatContract.ChatView chatView;


    private CustomChatInteractor() {
    }

    public static CustomChatInteractor getInstance() {
        if (instance == null)
            instance = new CustomChatInteractor();
        return instance;
    }


    public void setChatView(ChatContract.ChatView chatView) {
        this.chatView = chatView;
    }

    public String getCurrentChatPersonId() {
        return chatView.getChatId();
    }

    public boolean isViewValid() {
        return chatView != null;
    }

    public void reloadChat() {
        chatView.reloadRecentChat();
    }

}