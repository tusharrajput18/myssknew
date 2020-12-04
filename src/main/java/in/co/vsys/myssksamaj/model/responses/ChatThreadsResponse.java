package in.co.vsys.myssksamaj.model.responses;

import java.util.List;

import in.co.vsys.myssksamaj.model.data_models.ChatThreadModel;

/**
 * @author abhijeetjadhav
 */
public class ChatThreadsResponse extends Entity {
    private List<ChatThreadModel> data;

    public List<ChatThreadModel> getData() {
        return data;
    }
}