package ru.journal.fspoPrj.login_form.elements;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.login_form.config.Config;
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.storage.BufferedLink;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;

public class MainWindow extends LinearLayout implements View.OnTouchListener {

    private static final String SETTINGS_KEY = "User Settings";
    private static final String USER_NAME_KEY = "User Name";
    private static final String PASSWORD_KEY = "Password";
    private static final String SAVE_KEY = "Save me";

    private static final String ENTER_TITLE = "Войти";
    private static final String CHECK_BOX_TITLE = "Запомнить ";

    private static final String PASSWORD_TITLE = "Пароль";
    private static final String USER_NAME_TITLE = "Имя пользователя";

    private Context context;
    private CheckBox saveMe;
    private SerifTextView loginButton;
    private LoginForm userName, password;
    private SharedPreferences keyValueStorage;

    private static BufferedLink authBufferedLink;

    static {
        authBufferedLink = new BufferedLink(APIQuery.EMPTY_QUERY.getLink());
    }

    public MainWindow(Context context) {
        super(context);
        this.context = context;
        initFields();
    }

    public String getPassword() {
        try {
            return password.getText().toString();
        } catch (NullPointerException ex) {
            return GlobalConfig.EMPTY_STRING;
        }
    }

    public String getUserName() {
        try {
            return userName.getText().toString();
        } catch (NullPointerException ex) {
            return GlobalConfig.EMPTY_STRING;
        }
    }

    public BufferedLink getAuthBufferedLink() {
        return authBufferedLink;
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public void saveWindowInfo() {
        keyValueStorage = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString(USER_NAME_KEY, getUserName());
        editor.putString(PASSWORD_KEY, getPassword());
        editor.putBoolean(SAVE_KEY, saveMe.isChecked());
        editor.commit();
    }

    public void loadWindowInfo() {
        keyValueStorage = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE);
        if (keyValueStorage.getBoolean(SAVE_KEY, false)) {
            userName.setText(keyValueStorage.getString(USER_NAME_KEY, GlobalConfig.EMPTY_STRING));
            password.setText(keyValueStorage.getString(PASSWORD_KEY, GlobalConfig.EMPTY_STRING));
            saveMe.setChecked(true);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            setButtonInPressedColor();
            startButtonLogic();
        } else setButtonInUpColor();
        return true;
    }

    private void startButtonLogic() {
        if (Server.isNotMakingQueryNow(authBufferedLink.getQueryLink())) {
            loadConnectSettings();
            loginOnServer();
        }
    }

    private void loadConnectSettings() {
        keyValueStorage = context.getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, Context.MODE_PRIVATE);
    }

    private void loginOnServer() {
        if (isProxyON()) connectWithProxy();
        else defaultConnect();
    }

    private void connectWithProxy() {
        authBufferedLink = Server.connect(
                userName.getText().toString(),
                password.getText().toString(),
                getAddress(), getPort(), context);
    }

    private void defaultConnect() {
        authBufferedLink = Server.connect(userName.getText().toString(), password.getText().toString(), context);
    }

    public void startMainMenu() {
        context.startActivity(new Intent(context, MenuActivity.class));
    }

    private void setButtonInPressedColor() {
        loginButton.setBackgroundColor(Config.getButtonPressColor());
    }

    private void setButtonInUpColor() {
        loginButton.setBackgroundColor(Config.getFormColor());
    }

    public boolean isProxyON() {
        return keyValueStorage.getBoolean(MainSettingsActivity.CHECK_BOX_KEY, false);
    }

    public String getAddress() {
        return keyValueStorage.getString(MainSettingsActivity.IP_KEY, GlobalConfig.EMPTY_STRING);
    }

    public int getPort() {
        return Integer.parseInt(keyValueStorage.getString(MainSettingsActivity.PORT_KEY, GlobalConfig.EMPTY_STRING));
    }

    private void initFields() {
        super.setGravity(Gravity.CENTER);
        super.setOrientation(VERTICAL);
        super.addView(Config.getLogoView(context));
        super.setBackgroundDrawable(Config.getBackGround(context));

        userName = new LoginForm(context);
        password = new LoginForm(context);
        saveMe = new CheckBox(context);
        loginButton = new SerifTextView(context, ENTER_TITLE);

        saveMe.setTextColor(Config.getCheckBoxTextColor());
        saveMe.setTextSize(GlobalConfig.DEFAULT_TEXT_SIZE);
        saveMe.setText(CHECK_BOX_TITLE);

        loginButton.setLayoutParams(Config.getLoginButtonParam());
        loginButton.setTextColor(Config.getTextColor());
        loginButton.setBackgroundColor(Config.getFormColor());

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setHint(PASSWORD_TITLE);
        userName.setHint(USER_NAME_TITLE);

        LinearLayout checkBoxPlusButton = new LinearLayout(context);
        checkBoxPlusButton.setLayoutParams(Config.getLoginFormParam());
        checkBoxPlusButton.addView(saveMe);
        checkBoxPlusButton.addView(loginButton);

        super.addView(userName);
        super.addView(new TransparentHorizontalLine(context, Config.getLinesTransparentHeight()));
        super.addView(password);
        super.addView(new TransparentHorizontalLine(context, Config.getLinesTransparentHeight()));
        super.addView(checkBoxPlusButton);

        loginButton.setOnTouchListener(this);
    }

}
