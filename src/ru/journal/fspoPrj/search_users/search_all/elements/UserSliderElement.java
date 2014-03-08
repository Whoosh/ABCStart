package ru.journal.fspoPrj.search_users.search_all.elements;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.config.Config;

public class UserSliderElement extends LinearLayout {

    public static final String TAB = " \t";

    private volatile ImageView photoShower;
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
        horizontalLay.addView(separateLine());
        horizontalLay.addView(photoShower);
        horizontalLay.addView(
                createTextView(TAB + profileInfo.getFirstName() + TAB + profileInfo.getMiddleName() + TAB + profileInfo.getLastName()));
        addView(horizontalLay);
    }

    private VerticalLine separateLine() {
        return new VerticalLine(context, Config.getSeparateLineUserSliderNamesSize());
    }

    private SerifTextView createTextView(String text) {
        return new SerifTextView(context, text, Config.getUserSliderTextSize());
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

    public ProfileInfo getUserInfo() {
        return profileInfo;
    }
}
