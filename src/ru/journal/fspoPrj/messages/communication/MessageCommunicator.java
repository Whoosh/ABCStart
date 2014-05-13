package ru.journal.fspoPrj.messages.communication;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.util.ArrayList;

public class MessageCommunicator extends ServerCommunicator {

    public final static int MY_MESSAGES_QUERY = 1;

    private MessagesInfo messagesInfo;
    private String myMessageQuery;
    private int lastQueryID;
    private Activity parent;

    public MessageCommunicator(Activity parent) {
        this.parent = parent;
        makeMyMessageQuery();
    }

    public ArrayList<LightMessage> getMessages() {
        return messagesInfo.getLightMessages();
    }

    private void makeMyMessageQuery() {
        lastQueryID = MY_MESSAGES_QUERY;
        myMessageQuery = APIQuery.GET_MY_MESSAGE.getLink(getToken(), getYearID());
        sendQueryToServer(parent, makeExecutor());
    }

    @Override
    protected MainExecutor makeExecutor() {
        switch (lastQueryID) {
            case MY_MESSAGES_QUERY: {
                return new MessageExecutor(myMessageQuery, lastQueryID);
            }
        }
        return null;
    }

    public void cacheData(Intent data, int resultCode) {
        switch (resultCode) {
            case MY_MESSAGES_QUERY: {
                messagesInfo = new MessagesInfo(data.getStringExtra(myMessageQuery));
            }
            break;
        }
    }
}
