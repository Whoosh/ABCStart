package ru.journal.fspoPrj.search_users.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.*;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.action_bars.ExtendUserProfileBar;
import ru.journal.fspoPrj.search_users.profile.config.Config;
import ru.journal.fspoPrj.search_users.search_all.elements.PhotoMaker;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class ExtendUserProfileInfoActivity extends Activity implements View.OnClickListener {

    public static final String FIRST_NAME = "Имя : ";
    public static final String MIDDLE_NAME = "Отчество : ";
    public static final String LAST_NAME = "Фамилия : ";
    public static final String STUDENT = "Студент";
    public static final String STATUS = "Статус : ";
    public static final String TEACHER = "Преподаватель";
    public static final String GROUP = "Группа : ";
    public static final String MAIL = "Почта : ";
    public static final String TELL = "Телефон : ";
    public static final String SEND_MESSAGE_IN_SYSTEM = "Написать сообщение";
    public static final String SPACE = " ";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEACHER_AKA_STUDENT = "Студент/Преподаватель";

    public static final int DEFAULT_REQUEST = 1;

    private ProfileInfo userInfo;
    private LinearLayout mainLayout;
    private ImageView photo;
    private PhotoMaker photoMaker;
    private ExtendUserProfileBar userProfileBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        startFirst(savedInstanceState);
        initElements();
        addInfoOnElements();
        setContentView(mainLayout);
        startActionMode(userProfileBar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.drawable.ic_launcher_phone: {
                makeUserMyFriends();
            }
            break;
            case R.drawable.ic_mail_to: {
                mailToUser();
            }
            break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putSerializable(getClass().getCanonicalName(), userInfo);
        stopDoAny();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.userInfo = (ProfileInfo) savedInstanceState.getSerializable(getClass().getCanonicalName());
        addInfoOnElements();
    }

    private void addInfoOnElements() {
        startDownloadPhoto();
        if (userInfo == null) {
            return;
        }
        addMainInfo();
        addScrolledInfo();
    }

    private void addScrolledInfo() {
        ScrollView scroller = new ScrollView(this);
        LinearLayout infoLay = new LinearLayout(this);

        infoLay.setOrientation(LinearLayout.VERTICAL);
        if (!userInfo.getMail().isEmpty())
            setInfo(MAIL + userInfo.getMail(), R.drawable.ic_mail_to, infoLay);
        if (!userInfo.getPhone().isEmpty())
            setInfo(TELL + userInfo.getPhone(), R.drawable.ic_launcher_phone, infoLay);
        setInfo(SEND_MESSAGE_IN_SYSTEM, R.drawable.ic_send_in_system, infoLay);

        scroller.addView(infoLay);
        mainLayout.addView(scroller);
    }

    private void setInfo(String text, int res_id, LinearLayout informer) {
        LinearLayout horizontalLay = new LinearLayout(this);
        ImageView actionButton = new ImageView(this);
        actionButton.setImageResource(res_id);
        actionButton.setOnClickListener(this);
        actionButton.setId(res_id);

        SerifTextView textView = new SerifTextView(this, text, Config.getExtendUserInfoNamesTextSize());

        horizontalLay.setGravity(Gravity.CENTER_VERTICAL);
        horizontalLay.addView(actionButton);
        horizontalLay.addView(textView);

        informer.addView(horizontalLay);
        informer.addView(new HorizontalLine(this, Color.CYAN));
    }

    private void addMainInfo() {
        LinearLayout photoPlusNames = new LinearLayout(this);
        LinearLayout names = new LinearLayout(this);

        photoPlusNames.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Config.getExtendUserInfoPhotoHeight()));
        photo.setLayoutParams(new LinearLayout.LayoutParams(Config.getExtendUserInfoPhotoWidth(), LinearLayout.LayoutParams.WRAP_CONTENT));

        photoPlusNames.addView(photo);
        photoPlusNames.addView(new VerticalLine(this, Color.TRANSPARENT, Config.getSeparateTransparentNamesLineWidth()));
        photoPlusNames.addView(names);

        names.setOrientation(LinearLayout.VERTICAL);
        names.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        names.setGravity(Gravity.BOTTOM);

        setName(FIRST_NAME + userInfo.getFirstName(), names);
        setName(MIDDLE_NAME + userInfo.getMiddleName(), names);
        setName(LAST_NAME + userInfo.getLastName(), names);

        if (userInfo.isTeacher()) {
            setName(STATUS + TEACHER, names);
        } else if (userInfo.isStudent()) {
            setName(STATUS + STUDENT, names);
            setName(GROUP + userInfo.getStringGroup(), names);
        } else {
            setName(STATUS + TEACHER_AKA_STUDENT, names);
        }

        names.addView(new HorizontalLine(this, Color.TRANSPARENT, Config.getExtendUserInfoAfterNameSeparateHorizontalLineHeight()));

        mainLayout.addView(photoPlusNames);
        mainLayout.addView(new HorizontalLine(this, Color.BLACK, Config.getExtendUserInfoSeparateHorizontalMainInfoLineHeight()));
    }

    private void setName(String text, LinearLayout names) {
        names.addView(new SerifTextView(this, Gravity.CENTER_VERTICAL, text, Config.getExtendUserInfoNamesTextSize()));
        names.addView(new HorizontalLine(this, Color.CYAN));
    }

    private void startFirst(Bundle savedInstanceState) {
        if (getIntent() != null && savedInstanceState == null) {
            userInfo = ((ProfileInfo) getIntent().getSerializableExtra(getClass().getCanonicalName()));
        }
    }

    private void initElements() {
        mainLayout = new LinearLayout(this);
        photo = new ImageView(this);
        userProfileBar = new ExtendUserProfileBar(this);

        startDownloadPhoto();
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.LTGRAY);
    }

    private void stopDoAny() {
        if (photoMaker != null && !photoMaker.isCancelled()) {
            photoMaker.cancel(true);
        }
    }

    private void startDownloadPhoto() {
        if (userInfo != null) {
            try {
                photoMaker = new PhotoMaker(this, photo);
                photoMaker.execute(userInfo.getPhotoLink());
            } catch (NullPointerException ex) {
                Logger.printError(ex, getClass());
            }
        }
    }

    private void mailToUser() {
        Intent mailTo = new Intent();
        mailTo.setAction(Intent.ACTION_SEND);
        mailTo.setType(TEXT_PLAIN);
        mailTo.putExtra(android.content.Intent.EXTRA_EMAIL, userInfo.getMail());
        startActivityForResult(mailTo, DEFAULT_REQUEST);
    }

    private void makeUserMyFriends() {
        Intent data = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
        data.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        data.putExtra(ContactsContract.Intents.Insert.NAME,
                userInfo.getFirstName() + SPACE + userInfo.getMiddleName() + SPACE + userInfo.getLastName());
        data.putExtra(ContactsContract.Intents.Insert.EMAIL, userInfo.getMail());
        data.putExtra(ContactsContract.Intents.Insert.PHONE, userInfo.getPhone());
        startActivity(data);
    }
}
