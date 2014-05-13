package ru.journal.fspoPrj.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.messages.communication.ChatMessageCommunicator;
import ru.journal.fspoPrj.messages.communication.MessagesShowerAdapter;
import ru.journal.fspoPrj.messages.elements.ChatMessageFunctionBar;
import ru.journal.fspoPrj.messages.fragments.ChatButtonFragment;
import ru.journal.fspoPrj.messages.fragments.ChatSenderFragment;
import ru.journal.fspoPrj.messages.fragments.ChatShowerFragment;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class ChatMessageActivity extends Activity implements ChatButtonFragment.OnMessageSendClickListener {

    public static final String CHAT_ID_KEY = "ch_k";
    public static final String NEW_USER_CHAT_KEY = "n_uk";

    private static final String CHAT_SHOWER_FRAGMENT_TAG = "chatShowerFragment";
    private static final String CHAT_SENDER_FRAGMENT_TAG = "chatSenderFragment";
    private static final String CHAT_SENDER_BUTTON_TAG = "chatSenderButton";

    public static final String OLD_MESSAGE_KEY = "";
    public static final String EMPTY = "";
    public static final String WEB_SPACE = "%20";
    public static final String REGULAR_EXPRESSION_SPACE = "\\s+";

    private static ChatMessageCommunicator cMC;
    private static String chatID = "";

    private ChatMessageFunctionBar chatMessageFunctionBar;
    private ChatShowerFragment chatShowerFragment;
    private ChatButtonFragment chatButtonFragment;

    private String oldTypedMessage = "";
    private ChatSenderFragment chatSenderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_chat_fragment_layout);
        Intent data = getIntent();
        if (data != null && cMC == null) {
            if (data.hasExtra(CHAT_ID_KEY)) {
                chatID = data.getStringExtra(CHAT_ID_KEY);
                cMC = new ChatMessageCommunicator(this, chatID);
            } else if (data.hasExtra(NEW_USER_CHAT_KEY)) {
                cMC = new ChatMessageCommunicator(this);
                cMC.setSearchedUserID(data.getStringExtra(NEW_USER_CHAT_KEY));
            }
        }
        initElements();
    }

    private void initElements() {
        chatMessageFunctionBar = new ChatMessageFunctionBar(this);
        startActionMode(chatMessageFunctionBar);
    }

    private void addInfoOnElements() {
        try {
            chatShowerFragment = (ChatShowerFragment) getFragmentManager().findFragmentByTag(CHAT_SHOWER_FRAGMENT_TAG);
            chatSenderFragment = (ChatSenderFragment) getFragmentManager().findFragmentByTag(CHAT_SENDER_FRAGMENT_TAG);
            chatButtonFragment = (ChatButtonFragment) getFragmentManager().findFragmentByTag(CHAT_SENDER_BUTTON_TAG);

            chatShowerFragment.setChatData(this, cMC.getChatMessages());
            chatButtonFragment.setOnSendMessageClickListener(this);
            chatButtonFragment.setChatSenderFragment(chatSenderFragment);
            chatSenderFragment.setOldTypedMessage(oldTypedMessage);
        } catch (Exception ex) {
            Logger.printError(ex, getClass());
        }
    }


    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        if (chatSenderFragment != null) {
            outState.putString(OLD_MESSAGE_KEY, chatSenderFragment.getCurrentTypedText());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (cMC != null && cMC.getChatMessages() != null) {
            if (savedInstanceState != null) {
                oldTypedMessage = savedInstanceState.getString(OLD_MESSAGE_KEY);
                oldTypedMessage = oldTypedMessage == null ? EMPTY : oldTypedMessage;
            }
            addInfoOnElements();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ServerCommunicator.RESULT_FAIL) {
            onBackPressed();
        }
        if (data != null) {
            switch (resultCode) {
                case ChatMessageCommunicator.CHAT_QUERY: {
                    cMC.cacheData(data, resultCode);
                    addInfoOnElements();
                }
                break;
                case ChatMessageCommunicator.SEND_MESSAGE_QUERY: {
                    cMC = new ChatMessageCommunicator(this, chatID);
                }
                break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        setResult(MessagesShowerAdapter.REFRESH_REQUEST_CODE);
        cMC = null;
        super.onBackPressed();
    }

    @Override
    public void onMessageSendClick(String message) {
        message = message.replaceAll(REGULAR_EXPRESSION_SPACE, WEB_SPACE);
        cMC.sendMessageQuery(message, cMC.getChatMemberID());
    }
}
