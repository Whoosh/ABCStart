package ru.journal.fspoPrj.messages.communication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.messages.ChatMessageActivity;
import ru.journal.fspoPrj.messages.elements.MessageBox;

import java.util.ArrayList;

public class MessagesShowerAdapter extends BaseAdapter implements View.OnClickListener {

    public static final int REFRESH_REQUEST_CODE = 444;

    private ArrayList<LightMessage> lightMessages;
    private Activity parent;

    public MessagesShowerAdapter(Activity parent, ArrayList<LightMessage> lightMessages) {
        this.lightMessages = lightMessages;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return lightMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return lightMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Nullable
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view != null) {
            ((MessageBox) view).loadInfo(lightMessages.get(i));
        } else {
            view = new MessageBox(parent, lightMessages.get(i));
            view.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent date = new Intent();
        date.setClass(parent, ChatMessageActivity.class);
        date.putExtra(ChatMessageActivity.CHAT_ID_KEY,((MessageBox)view).getStringChatID());
        parent.startActivityForResult(date,REFRESH_REQUEST_CODE);
    }
}
