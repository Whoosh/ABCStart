package ru.journal.fspoPrj.messages.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.messages.communication.ChatInfoBuffer;
import ru.journal.fspoPrj.messages.communication.ChatMessage;
import ru.journal.fspoPrj.messages.communication.ChatMessagesAdapter;

import java.util.ArrayList;

public class ChatShowerFragment extends Fragment {

    public static final int DIVIDER_HEIGHT = 4;
    private LinearLayout mainLayout;
    private ListView chatList;
    private ChatInfoBuffer chatInfoBuffer;
    private Activity parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initElements();
        super.onCreate(savedInstanceState);
    }

    private void initElements() {
        mainLayout = new LinearLayout(getActivity());
        chatList = new ListView(getActivity());
        mainLayout.addView(chatList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mainLayout;
    }

    public void setChatData(Activity parent, ChatInfoBuffer chatInfoBuffer) {
        this.parent = parent;
        if (chatInfoBuffer != null) {
            this.chatInfoBuffer = chatInfoBuffer;
            setAdapter();
        }
    }

    private void setAdapter() {
        chatList.setAdapter(new ChatMessagesAdapter(parent, chatInfoBuffer));
        chatList.setDividerHeight(DIVIDER_HEIGHT);
    }

    public void resetData(Activity parent, ChatInfoBuffer chatInfoBuffer) {
        this.chatInfoBuffer = chatInfoBuffer;
        this.parent = parent;
        mainLayout.removeAllViews();
        initElements();
        setAdapter();
    }
}
