package ru.journal.fspoPrj.messages.communication;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.messages.elements.ChatElement;

import java.util.ArrayList;

public class ChatMessagesAdapter extends BaseAdapter {

    private ChatInfoBuffer chatInfoBuffer;
    private Activity parent;

    public ChatMessagesAdapter(Activity parent, ChatInfoBuffer chatInfoBuffer) {
        this.chatInfoBuffer = chatInfoBuffer;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return chatInfoBuffer.getChatMessages().size();
    }

    @Override
    public Object getItem(int i) {
        return chatInfoBuffer.getChatMessages().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Nullable
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = new ChatElement(parent, chatInfoBuffer, i);
        } else {
            ((ChatElement) view).resetData(chatInfoBuffer, i);
        }
        return view;
    }
}
