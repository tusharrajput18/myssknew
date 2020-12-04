package in.co.vsys.myssksamaj.model.data_models;

import java.io.Serializable;

/**
 * @author abhijeetjadhav
 */
public class ChatModel implements Serializable {
    private String ParentId = "0", ReceiverTypeId, ReceiverId, reply_senderId, Message, MessageStatusId,
            receiverName, reply_msg, reply_senderName, reply_ReceiverId, SenderId, date1, ChatId,
            senderName, MessageTypeId, reply_ReceiverName, reply_date, isBlocked, blockedBy,msgReadStatus="";
    private boolean isSelected=false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }

    public String getMsgReadStatus() {
        return msgReadStatus;
    }

    public void setMsgReadStatus(String msgReadStatus) {
        this.msgReadStatus = msgReadStatus;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getReceiverTypeId() {
        return ReceiverTypeId;
    }

    public void setReceiverTypeId(String receiverTypeId) {
        ReceiverTypeId = receiverTypeId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getReply_senderId() {
        return reply_senderId;
    }

    public void setReply_senderId(String reply_senderId) {
        this.reply_senderId = reply_senderId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageStatusId() {
        return MessageStatusId;
    }

    public void setMessageStatusId(String messageStatusId) {
        MessageStatusId = messageStatusId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReply_msg() {
        return reply_msg;
    }

    public void setReply_msg(String reply_msg) {
        this.reply_msg = reply_msg;
    }

    public String getReply_senderName() {
        return reply_senderName;
    }

    public void setReply_senderName(String reply_senderName) {
        this.reply_senderName = reply_senderName;
    }

    public String getReply_ReceiverId() {
        return reply_ReceiverId;
    }

    public void setReply_ReceiverId(String reply_ReceiverId) {
        this.reply_ReceiverId = reply_ReceiverId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageTypeId() {
        return MessageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        MessageTypeId = messageTypeId;
    }

    public String getReply_ReceiverName() {
        return reply_ReceiverName;
    }

    public void setReply_ReceiverName(String reply_ReceiverName) {
        this.reply_ReceiverName = reply_ReceiverName;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }
}