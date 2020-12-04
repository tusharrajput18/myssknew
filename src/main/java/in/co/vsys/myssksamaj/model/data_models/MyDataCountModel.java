package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

/**
 * @author abhijeetjadhav
 */
public class MyDataCountModel implements Serializable {
    private String You_Visted_Profile_Count = "";
    private String Who_Visted_Your_Profile_Count = "";
    private String You_Accept_SendRequest_Count = "";
    private String Your_SendRequest_NotAccept_Count = "";
    private String Your_ShortList_Count = "";
    private String Your_Not_ShortList_Count = "";
    private String Blocked_Count = "";
    private String Ignored_Count = "";

    public String getYou_Visted_Profile_Count() {
        return You_Visted_Profile_Count;
    }

    public String getWho_Visted_Your_Profile_Count() {
        return Who_Visted_Your_Profile_Count;
    }

    public String getYou_Accept_SendRequest_Count() {
        return You_Accept_SendRequest_Count;
    }

    public String getYour_SendRequest_NotAccept_Count() {
        return Your_SendRequest_NotAccept_Count;
    }

    public String getYour_ShortList_Count() {
        return Your_ShortList_Count;
    }

    public String getYour_Not_ShortList_Count() {
        return Your_Not_ShortList_Count;
    }

    public String getBlocked_Count() {
        return Blocked_Count;
    }

    public String getIgnored_Count() {
        return Ignored_Count;
    }
}