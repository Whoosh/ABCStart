package ru.journal.fspoPrj.login_form;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import ru.journal.fspoPrj.login_form.settings_form.MainSettingsActivity;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.configs.MainWindowConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.server_java.ServerErrors;

import java.util.concurrent.TimeoutException;

public class MainWindow extends LinearLayout implements View.OnTouchListener {

    private static final String SETTINGS_KEY = "User Settings";
    private static final String USER_NAME_KEY = "User Name";
    private static final String PASSWORD_KEY = "Password";
    private static final String SAVE_KEY = "Save me";

    private static final String ENTER_TITLE = "Войти";
    private static final String CHECK_BOX_TITLE = "Запомнить ";

    private static final String PASSWORD_TITLE = "Пароль";
    private static final String USER_NAME_TITLE = "Имя пользователя";

    private SharedPreferences keyValueStorage;
    private CustomLoginEditText userName;
    private CustomLoginEditText password;
    private Context context;
    private CheckBox saveMe;
    private SerifTextView login;

    public MainWindow(Context context) {
        super(context);
        super.setGravity(Gravity.CENTER);
        super.setOrientation(VERTICAL);
        super.addView(MainWindowConfig.getLogoView(context));
        super.setBackgroundDrawable(MainWindowConfig.getBackGround(context));
        LinearLayout checkBoxPlusButton = new LinearLayout(context);

        this.context = context;
        userName = new CustomLoginEditText(context);

        saveMe = new CheckBox(context);
        saveMe.setTextColor(MainWindowConfig.getCheckBoxTextColor());
        saveMe.setTextSize(GlobalConfig.DEFAULT_TEXT_SIZE);
        saveMe.setText(CHECK_BOX_TITLE);

        login = new SerifTextView(context, ENTER_TITLE);
        login.setLayoutParams(new LayoutParams(
                MainWindowConfig.getLoginButtonWidth(),
                MainWindowConfig.getLoginButtonHeight()));
        login.setTextColor(MainWindowConfig.getTextColor());
        login.setBackgroundColor(MainWindowConfig.getFormColor());

        password = new CustomLoginEditText(context);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setHint(PASSWORD_TITLE);
        userName.setHint(USER_NAME_TITLE);

        checkBoxPlusButton.setLayoutParams(new LayoutParams(
                MainWindowConfig.getFormWidth(),
                MainWindowConfig.getFormHeight()));
        checkBoxPlusButton.addView(saveMe);
        checkBoxPlusButton.addView(login);

        super.addView(userName);
        super.addView(new TransparentHorizontalLine(context, MainWindowConfig.getLinesTransparentHeight()));
        super.addView(password);
        super.addView(new TransparentHorizontalLine(context, MainWindowConfig.getLinesTransparentHeight()));
        super.addView(checkBoxPlusButton);

        login.setOnTouchListener(this);
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
        if (view.equals(login) && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            setButtonInPressedColor();
            startButtonLogic();
        } else if (view.equals(login) && motionEvent.getAction() == MotionEvent.ACTION_UP)
            setButtonInUpColor();
        return true;
    }

    private void startButtonLogic() {
        SharedPreferences proxyInfo = context.getSharedPreferences
                (MainSettingsActivity.SETTINGS_KEY, Context.MODE_PRIVATE);
        boolean proxyIsON = proxyInfo.getBoolean(MainSettingsActivity.CHECK_BOX_KEY, false);

        try {
            if (proxyIsON) loginOnServerWithProxy(proxyInfo);
            else loginOnServer();
            checkAuthorizationInfo();
            startMainMenu();
        } catch (ServerDownException e) {
            messageOnScreen(ServerErrors.SERVER_IS_DOWN.message());
        } catch (TimeoutException e) {
            messageOnScreen(ServerErrors.SERVER_TTL_QUERY_ERROR.message());
        } catch (PasswordErrorException e) {
            messageOnScreen(ServerErrors.LOGIN_OR_PASSWORD_ERROR.message());
        }
    }

    private void loginOnServerWithProxy(SharedPreferences proxyInfo) throws TimeoutException, ServerDownException {
        String address = proxyInfo.getString(MainSettingsActivity.IP_KEY, GlobalConfig.EMPTY_STRING);
        int port = Integer.parseInt(proxyInfo.getString(MainSettingsActivity.PORT_KEY, GlobalConfig.EMPTY_STRING));
        loginOnServerWithProxy(address, port);
    }

    private void messageOnScreen(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void loginOnServer() throws ServerDownException, TimeoutException {
        if (Server.isNotAlive()) throw new ServerDownException();
        Server.connect(userName.getText().toString(), password.getText().toString());
    }

    private void loginOnServerWithProxy(String address, int port) throws ServerDownException, TimeoutException {
        if (Server.isNotAlive(address, port)) throw new ServerDownException();
        Server.connect(userName.getText().toString(), password.getText().toString(), address, port);
    }

    private void checkAuthorizationInfo() throws PasswordErrorException {
        if (Server.passwordIsWrong()) throw new PasswordErrorException();
    }

    private void startMainMenu() {
        context.startActivity(new Intent(context, MenuActivity.class));
    }

    private void setButtonInPressedColor() {
        login.setBackgroundColor(MainWindowConfig.getButtonPressColor());
    }

    private void setButtonInUpColor() {
        login.setBackgroundColor(MainWindowConfig.getFormColor());
    }

    private class CustomLoginEditText extends EditText {

        public CustomLoginEditText(Context context) {
            super(context);
            super.setLayoutParams(new LayoutParams(
                    MainWindowConfig.getFormWidth(),
                    MainWindowConfig.getFormHeight()));
            super.setBackgroundColor(MainWindowConfig.getFormColor());
            super.setTextColor(MainWindowConfig.getTextColor());
            super.setTextSize(GlobalConfig.DEFAULT_TEXT_SIZE);
            super.setText(GlobalConfig.EMPTY_STRING);
            super.setGravity(Gravity.CENTER_VERTICAL);
            super.setTypeface(Typeface.SERIF);
            super.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
            super.setHintTextColor(MainWindowConfig.getTextColor());
        }
    }

    private class ServerDownException extends Throwable {
    }

    private class PasswordErrorException extends Throwable {
    }
}
