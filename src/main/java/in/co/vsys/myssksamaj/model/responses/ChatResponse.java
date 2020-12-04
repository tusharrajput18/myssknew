package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.ChatModel;

/**
 * @author abhijeetjadhav
 */
public class ChatResponse extends Entity {
    private List<ChatModel> data;

    public List<ChatModel> getData() {
        return data;
    }
}