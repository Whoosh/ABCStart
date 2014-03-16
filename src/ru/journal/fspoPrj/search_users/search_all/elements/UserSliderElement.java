package ru.journal.fspoPrj.search_users.search_all.elements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.config.Config;

public class UserSliderElement extends LinearLayout {


    private ImageView photoShower;
    private ProfileInfo profileInfo;
    private Context context;
    private PhotoMaker photoMaker;

    public UserSliderElement(Context context, ProfileInfo profileInfo) {
        super(context);
        this.context = context;
        this.profileInfo = profileInfo;
        this.setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        addElements();
    }

    public ProfileInfo getUserInfo() {
        return profileInfo;
    }

    public void loadInfo(ProfileInfo profileInfo) {
        if (photoMaker != null && !photoMaker.isCancelled()) {
            photoMaker.cancel();
        }
        this.profileInfo = profileInfo;
        removeAllViews();
        addElements();
    }

    private void addElements() {
        LinearLayout horizontalLay = new LinearLayout(context);
        horizontalLay.setGravity(Gravity.CENTER_VERTICAL);
        createPhoto();
        horizontalLay.addView(photoShower);
        horizontalLay.addView(getSeparateLine());
        horizontalLay.addView(getVerticalInfo());
        addView(horizontalLay);
    }

    private View getVerticalInfo() {
        LinearLayout verticalLay = new LinearLayout(context);
        verticalLay.setOrientation(VERTICAL);
        verticalLay.setGravity(Gravity.CENTER_VERTICAL);
        verticalLay.addView(createTextView(profileInfo.getPointedFirstName()));
        verticalLay.addView(getDGHLine());
        verticalLay.addView(createTextView(profileInfo.getPointedMiddleName()));
        verticalLay.addView(getDGHLine());
        verticalLay.addView(createTextView(profileInfo.getPointedLastName()));
        verticalLay.addView(getDGHLine());
        return verticalLay;
    }

    private HorizontalLine getDGHLine() {
        return new HorizontalLine(context, Color.DKGRAY);
    }

    private VerticalLine getSeparateLine() {
        return new VerticalLine(context, Color.TRANSPARENT, Config.getInfoSeparateLineWidth());
    }

    private View createTextView(String text) {
        TextView textView = new TextView(context);
        textView.setTypeface(Typeface.SANS_SERIF);
        textView.setText(text);
        return textView;
    }

    private void createPhoto() {
        photoShower = new ImageView(context);
        photoShower.setLayoutParams(new ListView.LayoutParams(Config.getUserSliderPhotoWidth(), Config.getUserSliderPhotoHeight()));
        if (profileInfo.getPhotoLink().isEmpty()) {
            photoShower.setImageResource(R.drawable.ic_ghost);
        } else {
            photoMaker = new PhotoMaker(context, photoShower);
            photoMaker.execute(profileInfo.getPhotoLink());
        }
    }

}
