package ru.journal.fspoPrj.messages.communication;


import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class ChatMessageCommunicator extends ServerCommunicator {

    public static final int CHAT_QUERY = 1;
    public static final int SEND_MESSAGE_QUERY = 2;

    private int lastQueryID;
    private String chatMessagesQuery;
    private String sendMessageQuery;
    private Activity parent;
    private String chatID;
    private ChatInfoBuffer chatInfoBuffer;

    public ChatMessageCommunicator(Activity parent) {
        this.parent = parent;
        this.chatID = "";
    }

    public ChatMessageCommunicator(Activity parent, String chatID) {
        this.parent = parent;
        this.chatID = chatID;
        makeChatQuery();
    }

    public String getChatID() {
        return chatID;
    }

    public ChatInfoBuffer getChatMessages() {
        return chatInfoBuffer;
    }

    private void makeChatQuery() {
        lastQueryID = CHAT_QUERY;
        chatMessagesQuery = APIQuery.GET_CHAT_MESSAGE.getLink(getToken(), chatID);
        sendQueryToServer(parent, makeExecutor());
    }

    @Override
    protected MainExecutor makeExecutor() {
        switch (lastQueryID) {
            case CHAT_QUERY: {
                return new ChatMessagesExecutor(lastQueryID, chatMessagesQuery);
            }
            case SEND_MESSAGE_QUERY: {
                return new SendMessageQueryExecutor(lastQueryID, sendMessageQuery);
            }
        }
        return null;
    }

    public void cacheData(Intent data, int resultCode) {
        switch (resultCode) {
            case CHAT_QUERY: {
                chatInfoBuffer = new ChatInfoBuffer(data.getStringExtra(chatMessagesQuery));
                chatInfoBuffer.markMessages(getMyID());
            }
            break;
        }
    }

    public void setSearchedUserID(String searchedUserID) {
        this.chatID = searchedUserID;
    }

    public void sendMessageQuery(String message, String receiverID) {
        lastQueryID = SEND_MESSAGE_QUERY;
        sendMessageQuery = APIQuery.GET_SENT_MESSAGE.getLink(getToken(), receiverID, message);
        sendQueryToServer(parent, makeExecutor());
    }

    public String getChatMemberID() {
        if (chatInfoBuffer == null)
            return chatID;
        if (chatInfoBuffer.getChatMessages().size() > 0) {
            if (chatInfoBuffer.getChatMessages().get(0).isMyMessage())
                return chatInfoBuffer.getChatMessages().get(0).getTOIDString();
            else
                return chatInfoBuffer.getChatMessages().get(0).getFromIDString();
        } else {
            return chatID;
        }
    }
}
