package ru.journal.fspoPrj.messages.elements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import ru.journal.fspoPrj.messages.Config;
import ru.journal.fspoPrj.messages.communication.ChatInfoBuffer;
import ru.journal.fspoPrj.search_users.search_all.elements.PhotoMaker;

public class ChatElement extends LinearLayout {

    public static final int PADDING_SIZE = 5;
    private Activity parent;
    private ChatInfoBuffer chatInfoBuffer;
    private PhotoMaker photoMaker;
    private ImageView photo;
    private int index;
    private TextView message;

    public ChatElement(Activity parent, ChatInfoBuffer chatInfoBuffer, int index) {
        super(parent);
        this.parent = parent;
        this.chatInfoBuffer = chatInfoBuffer;
        this.index = index;
        setInfoOnElement();
        super.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void resetData(ChatInfoBuffer chatInfoBuffer, int index) {
        if (photoMaker != null && !photoMaker.isCancelled()) {
            photoMaker.cancel();
        }
        removeAllViews();
        this.chatInfoBuffer = chatInfoBuffer;
        this.index = index;
        setInfoOnElement();
    }

    private void setInfoOnElement() {
        photo = new ImageView(parent);
        photo.setLayoutParams(new ViewGroup.LayoutParams(Config.getPhotoWidth(), Config.getPhotoHeight()));
        photoMaker = new PhotoMaker(parent, photo);
        setMessage();
        if (chatInfoBuffer.getChatMessages().get(index).isMyMessage()) {
            photoMaker.execute(chatInfoBuffer.getNormalFromPhotoLink());
            setLeftColor();
        } else {
            photoMaker.execute(chatInfoBuffer.getNormalToPhotoLink());
            setRightColor();
        }
    }

    private void setMessage() {
        message = new TextView(parent);
        message.setText(chatInfoBuffer.getChatMessages().get(index).getMessageText());
        message.setLayoutParams(new LayoutParams(
                ((WindowManager) parent
                        .getApplicationContext()
                        .getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay()
                        .getWidth()
                        - Config.getPhotoWidth(), ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        message.setPadding(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE);
        message.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setLeftColor() {
        super.setBackgroundColor(Color.WHITE);
        message.setGravity(Gravity.LEFT);
        addView(photo);
        addView(message);
    }

    private void setRightColor() {
        super.setBackgroundColor(Color.LTGRAY);
        message.setGravity(Gravity.RIGHT);
        addView(message);
        addView(photo);
    }
}
