package ru.journal.fspoPrj.user_profile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.profile_info.JsonKeys;
import ru.journal.fspoPrj.server_java.profile_info.UserProfile;
import ru.journal.fspoPrj.user_profile.config.Config;
import ru.journal.fspoPrj.user_profile.elements.PhotoBoard;
import ru.journal.fspoPrj.user_profile.elements.PhotoMaker;
import ru.journal.fspoPrj.user_profile.elements.TextInfo;

import java.util.concurrent.TimeoutException;

public class ProfileActivity extends Activity {

    public static final String USER_ID_KEY = "UsID";

    private LinearLayout mainLayout;
    private FrameLayout photoLayout;
    private LinearLayout infoLay;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        prepareActivity();
    }

    private void prepareActivity() {
        try {
            lookingOnRequestsProfile();
            loadAllInfoOnScreen();
        } catch (TimeoutException e) {
            setResult(MenuActivity.SERVER_CONNECTION_DIE);
            finish();
        }
    }

    private void lookingOnRequestsProfile() throws TimeoutException {
        String userID = getIntent().getStringExtra(USER_ID_KEY);
       // userProfile = userID == null ? Server.getMyProfile() : Server.getUserProfile(userID);
        //TODO
    }

    private void loadAllInfoOnScreen() {
        initAllVisualElements();
        addInfoOnScreen();
        addPhotoOnScreen();
        setContentView(mainLayout);
    }

    private void initAllVisualElements() {
        infoLay = new LinearLayout(this);
        infoLay.setOrientation(LinearLayout.VERTICAL);

        photoLayout = new FrameLayout(this);
        photoLayout.setLayoutParams(Config.getPhotoLayParams());

        mainLayout = new LinearLayout(this);
        mainLayout.setBackgroundColor(Config.getBackgroundColor());
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(photoLayout);
        mainLayout.addView(infoLay);
    }

    private void addInfoOnScreen() {
        infoLay.addView(new TransparentHorizontalLine(this, Config.vacuumHeight));

        for (int i = 0; i < TextInfo.values().length; i++) {
            infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
            infoLay.addView(new SerifTextView(this,
                    Gravity.LEFT, TextInfo
                    .values()[i]
                    .getVisualKey() + userProfile
                    .getInfo(TextInfo.values()[i].getChainKey()),
                    GlobalConfig.HEADER_TEXT_SIZE));
            infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        }
    }

    private void addPhotoOnScreen() {
        ImageView photoView = new ImageView(this);

        new PhotoMaker(this, photoView).execute(userProfile.getInfo(JsonKeys.PHOTO.getChainKey()));

        photoLayout.addView(photoView);
        photoLayout.addView(new PhotoBoard(this));
    }
}
