package ru.journal.fspoPrj.login_form.elements;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.login_form.config.Config;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationCommunicator;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;

public class MainWindow extends LinearLayout implements View.OnTouchListener {

    public static final String USER_NAME_KEY = "k_name";
    public static final String PASSWORD_KEY = "k_password";

    private static final String SAVE_KEY = "k_save";
    private static final String STATE_KEY = "k_settings";

    private static final String ENTER_TITLE = "Войти";
    private static final String CHECK_BOX_TITLE = "Запомнить ";

    private static final String PASSWORD_TITLE = "Пароль";
    private static final String USER_NAME_TITLE = "Имя пользователя";

    private static final String EMPTY = "";

    private Activity context;
    private CheckBox saveMe;
    private SerifTextView loginButton;
    private LoginForm userName, password;
    private SharedPreferences keyValueStorage;
    private AuthorizationCommunicator authorization;

    public MainWindow(Activity context, AuthorizationCommunicator authorization) {
        super(context);
        this.context = context;
        this.authorization = authorization;
        initFields();
    }

    public String getPassword() {
        try {
            return password.getText().toString();
        } catch (NullPointerException ex) {
            return EMPTY;
        }
    }

    public String getUserName() {
        try {
            return userName.getText().toString();
        } catch (NullPointerException ex) {
            return EMPTY;
        }
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public void saveCurrentInfo() {
        keyValueStorage = context.getSharedPreferences(STATE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString(USER_NAME_KEY, getUserName());
        editor.putString(PASSWORD_KEY, getPassword());
        editor.putBoolean(SAVE_KEY, saveMe.isChecked());
        editor.commit();
    }

    public void restoreInfo() {
        keyValueStorage = context.getSharedPreferences(STATE_KEY, Context.MODE_PRIVATE);
        if (keyValueStorage.getBoolean(SAVE_KEY, false)) {
            userName.setText(keyValueStorage.getString(USER_NAME_KEY, EMPTY));
            password.setText(keyValueStorage.getString(PASSWORD_KEY, EMPTY));
            saveMe.setChecked(true);
        }
    }

    public boolean isProxyON() {
        return keyValueStorage.getBoolean(MainSettingsActivity.CHECK_BOX_KEY, false);
    }

    public String getAddress() {
        return keyValueStorage.getString(MainSettingsActivity.IP_KEY, EMPTY);
    }

    public int getPort() {
        return Integer.parseInt(keyValueStorage.getString(MainSettingsActivity.PORT_KEY, EMPTY));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            loginButton.setBackgroundColor(Config.getButtonPressColor());
            this.startButtonLogic();
        } else {
            loginButton.setBackgroundColor(Config.getFormColor());
        }
        return true;
    }

    private void startButtonLogic() {
        loadConnectSettings();
        loginOnServer();
    }

    private void loadConnectSettings() {
        keyValueStorage = context.getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, Context.MODE_PRIVATE);
    }

    private void loginOnServer() {
        if (isProxyON()) {
            authorization.authWithProxy(context, userName.getText().toString(), password.getText().toString(), getAddress(), getPort());
        } else {
            authorization.authNormal(context, userName.getText().toString(), password.getText().toString());
        }
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
        saveMe.setTextSize(GlobalConfig.getDefaultTextSize());
        saveMe.setText(CHECK_BOX_TITLE);

        loginButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        loginButton.setTextColor(Config.getTextColor());
        loginButton.setBackgroundColor(Config.getFormColor());

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setHint(PASSWORD_TITLE);
        userName.setHint(USER_NAME_TITLE);

        LinearLayout checkBoxPlusButton = new LinearLayout(context);
        checkBoxPlusButton.setLayoutParams(new LayoutParams(Config.getFormWidth(), Config.getFormHeight()));
        checkBoxPlusButton.addView(saveMe);
        checkBoxPlusButton.addView(loginButton);

        super.addView(userName);
        super.addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getLinesTransparentHeight()));
        super.addView(password);
        super.addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getLinesTransparentHeight()));
        super.addView(checkBoxPlusButton);

        loginButton.setOnTouchListener(this);
    }
}
