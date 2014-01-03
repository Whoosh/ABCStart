package ru.journal.fspoPrj.user_profile;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.configs.ProfileConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.profile_info.JsonValues;


import java.util.concurrent.TimeoutException;

public class ProfileActivity extends Activity {

    public static final String USER_ID_KEY = "UsID";
    // TODO
    private LinearLayout mainLayout;
    private FrameLayout imgLay;
    private LinearLayout infoLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        prepareActivity();
        loadAllInfoOnScreen();
    }

    private void prepareActivity() {
        try {
            MenuActivity.SERVER_HAS_CONNECTED_ERROR = false;
            lookingOnRequestsProfile();
        } catch (TimeoutException e) {
            MenuActivity.SERVER_HAS_CONNECTED_ERROR = true;
            finish();
        }
    }

    private void lookingOnRequestsProfile() throws TimeoutException {
        String userID = getIntent().getStringExtra(USER_ID_KEY);
        if (userID == null) {
            Server.loadMyProfileInToProfileInfo();
        } else {
            Server.loadAnyUserInfoInToProfileInfo(userID);
        }
    }

    private void loadAllInfoOnScreen() {
        initAllVisualElements();
        addInfoOnScreen();
        addPhotoOnScreen();
        setContentView(mainLayout);
    }

    private void initAllVisualElements() {
        mainLayout = new LinearLayout(this);
        infoLay = new LinearLayout(this);
        imgLay = new FrameLayout(this);

        infoLay.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams imgLayParams = new LinearLayout.LayoutParams(
                ProfileConfig.imgLayWidth,
                ProfileConfig.imgLayHeight);
        imgLayParams.setMargins(ProfileConfig.imtLayMarginLeft, ProfileConfig.imgLayMarginTop, 0, 0);

        imgLay.setLayoutParams(imgLayParams);

        mainLayout.setBackgroundColor(Color.WHITE);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(imgLay);
        mainLayout.addView(infoLay);
    }

    private void addInfoOnScreen() {
        infoLay.addView(new TransparentHorizontalLine(this, ProfileConfig.vacuumHeight));

        for (int i = 0; i < ShowingsValue.values().length; i++) {
            infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
            infoLay.addView(new SerifTextView(this,
                    Gravity.LEFT, ShowingsValue.values()[i].getVisualKey()
                    + JsonValues.values()[ShowingsValue.values()[i].getChainKey()].getValue(),
                    GlobalConfig.HEADER_TEXT_SIZE));
            infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        }
    }

    private void addPhotoOnScreen() {
        ImageView photoView = new ImageView(this);
        new PhotoMaker(this, photoView).execute(JsonValues.PHOTO_LINK.getValue());
        imgLay.addView(photoView);
        addBoardToPhotoScreen();
    }

    private void addBoardToPhotoScreen() {
        imgLay.addView(new PhotoBoard(this));
    }
}
