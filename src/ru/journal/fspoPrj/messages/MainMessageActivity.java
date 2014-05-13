package ru.journal.fspoPrj.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import ru.journal.fspoPrj.messages.communication.MessageCommunicator;
import ru.journal.fspoPrj.messages.communication.MessagesShowerAdapter;
import ru.journal.fspoPrj.messages.elements.MainMessageFunctionBar;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class MainMessageActivity extends Activity {

    private static final int DIVIDER_HEIGHT = 10;

    private LinearLayout mainLayout;
    private MainMessageFunctionBar mainMessageFunctionBar;
    private ListView messageListView;

    private static MessageCommunicator mC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initElements();

        if (mC == null) {
            mC = new MessageCommunicator(this);
        }

        startActionMode(mainMessageFunctionBar);
        setContentView(mainLayout);
    }

    private void initElements() {
        mainMessageFunctionBar = new MainMessageFunctionBar(this);
        mainLayout = new LinearLayout(this);
        messageListView = new ListView(this);
        messageListView.setDividerHeight(DIVIDER_HEIGHT);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(messageListView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ServerCommunicator.RESULT_FAIL) {
            finish();
        }
        if (resultCode == MessagesShowerAdapter.REFRESH_REQUEST_CODE) {
            mainLayout.removeAllViews();
            mainLayout.addView(messageListView);
            mC = new MessageCommunicator(this);
        }
        if (data != null && resultCode == MessageCommunicator.MY_MESSAGES_QUERY) {
            handleResponse(data, resultCode);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (mC != null && mC.getMessages() != null) {
            messageListView.setAdapter(new MessagesShowerAdapter(this, mC.getMessages()));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        mC = null;
        super.onBackPressed();
    }

    private void handleResponse(Intent data, int resultCode) {
        switch (resultCode) {
            case MessageCommunicator.MY_MESSAGES_QUERY: {
                mC.cacheData(data, resultCode);
                messageListView.setAdapter(new MessagesShowerAdapter(this, mC.getMessages()));
            }
            break;
        }
    }
}
