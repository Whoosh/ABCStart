package ru.journal.fspoPrj.messages.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.messages.ChatMessageActivity;

public class ChatButtonFragment extends Fragment implements View.OnClickListener {

    private static final String SEND_MESSAGE = "Отправить";

    private LinearLayout mainLayout;
    private Button sendButton;
    private OnMessageSendClickListener onMessageSendClickListener;
    private ChatSenderFragment chatSenderFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initElements();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mainLayout;
    }

    private void initElements() {
        mainLayout = new LinearLayout(getActivity());

        sendButton = new Button(getActivity());
        sendButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sendButton.setText(SEND_MESSAGE);
        sendButton.setOnClickListener(this);

        mainLayout.addView(sendButton);
        mainLayout.setGravity(Gravity.BOTTOM);
    }

    public void setChatSenderFragment(ChatSenderFragment chatSenderFragment) {
        this.chatSenderFragment = chatSenderFragment;
    }

    public void setOnSendMessageClickListener(OnMessageSendClickListener listener) {
        this.onMessageSendClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        try {
            onMessageSendClickListener.onMessageSendClick(chatSenderFragment.getCurrentTypedText());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public interface OnMessageSendClickListener {
        void onMessageSendClick(String currentMessage);
    }
}
