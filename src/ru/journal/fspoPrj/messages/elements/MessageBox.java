package ru.journal.fspoPrj.messages.elements;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ru.journal.fspoPrj.messages.Config;
import ru.journal.fspoPrj.messages.communication.LightMessage;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.search_users.search_all.elements.PhotoMaker;

public class MessageBox extends LinearLayout {

    private static final String SPACE = " ";
    public static final int SEPARATE_LINE_COLOR = Color.parseColor("#D9D3D3");
    public static final int TRANSPARENT_SEPARATE_HEIGHT = 10;
    private Context parent;
    private LightMessage lightMessage;
    private ImageView photo;
    private PhotoMaker photoMaker;
    private LinearLayout textInfoLayout;
    private TextView userName;
    private TextView messageData;
    private TextView date;

    public MessageBox(Context context, LightMessage lightMessage) {
        super(context);
        this.parent = context;
        this.lightMessage = lightMessage;
        addElements();
    }

    private void addElements() {
        initElements();
        setInfoOnForms();
        setTextInfoOnLay();
        addView(photo);
        addView(textInfoLayout);
        photoMaker.execute(lightMessage.getPhotoLink());
    }

    private void setInfoOnForms() {
        userName.setText(lightMessage.getFirstName() + SPACE + lightMessage.getLastName());
        photoMaker = new PhotoMaker(parent, photo);
        messageData.setText(lightMessage.getText());
        date.setText(lightMessage.getDay() + SPACE + lightMessage.getMonth() + SPACE + lightMessage.getTime());
    }

    private void initElements() {
        photo = new ImageView(parent);
        photo.setLayoutParams(new ViewGroup.LayoutParams(Config.getPhotoWidth(), Config.getPhotoHeight()));
        textInfoLayout = new LinearLayout(parent);
        textInfoLayout.setBackgroundColor(Color.TRANSPARENT);
        setBackgroundColor(Color.TRANSPARENT);
        userName = new TextView(parent);
        userName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        userName.setBackgroundColor(Color.WHITE);
        messageData = new TextView(parent);
        date = new TextView(parent);
    }

    private void setTextInfoOnLay() {
        textInfoLayout.setOrientation(VERTICAL);
        textInfoLayout.addView(userName);
        textInfoLayout.addView(new HorizontalLine(parent, SEPARATE_LINE_COLOR));
        textInfoLayout.addView(new HorizontalLine(parent,Color.TRANSPARENT, TRANSPARENT_SEPARATE_HEIGHT));
        textInfoLayout.addView(messageData);
    }

    public void loadInfo(LightMessage lightMessage) {
        if (photoMaker != null && !photoMaker.isCancelled()) {
            photoMaker.cancel();
        }
        removeAllViews();
        this.lightMessage = lightMessage;
        addElements();
    }

    public String getStringChatID() {
        return lightMessage.getStringChatUserID();
    }
}
