package com.example.First_prj.LoginForm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.First_prj.FirstActivitySettings.MainSettingsActivity;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.TransparentHorizontalLine;
import com.example.First_prj.ForAllCode.GlobalConfig;
import com.example.First_prj.ForAllCode.GlobalConstants;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.JavaServer.Server;
import com.example.First_prj.MenuAndSwitchers.MenuActivity;

import java.util.concurrent.TimeoutException;

public class MainWindow extends LinearLayout implements View.OnTouchListener {

    private static final String SERVER_IS_DOWN = "Отсутсвует подключение";
    private static final String LOGIN_OR_PASSWORD_ERROR = "Неверный логин или пароль";
    private static final String SERVER_TTL_QUERY_ERROR = "Сервер не отвечает, проверьте подлючение и попробуйте ещё раз";

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
        super.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        super.setGravity(Gravity.CENTER);
        super.setOrientation(VERTICAL);
        super.addView(GlobalConfig.MainWindowConfig.getLogoView(context));
        super.setBackgroundDrawable(GlobalConfig.MainWindowConfig.getBackGround(context));
        LinearLayout checkBoxPlusButton = new LinearLayout(context);

        this.context = context;
        userName = new CustomLoginEditText(context);

        saveMe = new CheckBox(context);
        saveMe.setTextColor(GlobalConfig.MainWindowConfig.getCheckBoxTextColor());
        saveMe.setTextSize(GlobalConstants.DEFAULT_TEXT_SIZE);
        saveMe.setText(CHECK_BOX_TITLE);

        login = new SerifTextView(context, ENTER_TITLE);
        login.setLayoutParams(new ViewGroup.LayoutParams(
                GlobalConfig.MainWindowConfig.getLoginButtonWidth(),
                GlobalConfig.MainWindowConfig.getLoginButtonHeight()));
        login.setTextColor(GlobalConfig.MainWindowConfig.getTextColor());
        login.setBackgroundColor(GlobalConfig.MainWindowConfig.getFormColor());

        password = new CustomLoginEditText(context);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setHint(PASSWORD_TITLE);
        userName.setHint(USER_NAME_TITLE);

        checkBoxPlusButton.setLayoutParams(new ViewGroup.LayoutParams(
                GlobalConfig.MainWindowConfig.getFormWidth(),
                GlobalConfig.MainWindowConfig.getFormHeight()));
        checkBoxPlusButton.addView(saveMe);
        checkBoxPlusButton.addView(login);

        super.addView(userName);
        super.addView(new TransparentHorizontalLine(context, GlobalConfig.MainWindowConfig.getLinesTransparentHeight()));
        super.addView(password);
        super.addView(new TransparentHorizontalLine(context, GlobalConfig.MainWindowConfig.getLinesTransparentHeight()));
        super.addView(checkBoxPlusButton);

        login.setOnTouchListener(this);
    }

    public String getPassword() {
        try {
            return password.getText().toString();
        } catch (NullPointerException ex) {
            return GlobalConstants.EMPTY_STRING;
        }
    }

    public String getUserName() {
        try {
            return userName.getText().toString();
        } catch (NullPointerException ex) {
            return GlobalConstants.EMPTY_STRING;
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
            userName.setText(keyValueStorage.getString(USER_NAME_KEY, GlobalConstants.EMPTY_STRING));
            password.setText(keyValueStorage.getString(PASSWORD_KEY, GlobalConstants.EMPTY_STRING));
            saveMe.setChecked(true);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.equals(login) && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            startButtonLogic();
            setButtonInPressedColor();
        } else if (view.equals(login) && motionEvent.getAction() == MotionEvent.ACTION_UP)
            setButtonInUpColor();
        return true;
    }

    private void startButtonLogic() {
        SharedPreferences proxyInfo = context.getSharedPreferences
                (MainSettingsActivity.SETTINGS_KEY, Context.MODE_PRIVATE);
        try {
            try {
                onButtonPressed(proxyInfo);
            } catch (ProxyDetectException e) {
                startServerWithProxy(proxyInfo);
            }
        } catch (ServerDownException e) {
            messageOnScreen(SERVER_IS_DOWN);
        } catch (TimeoutException e) {
            messageOnScreen(SERVER_TTL_QUERY_ERROR);
        } catch (PasswordErrorException e) {
            messageOnScreen(LOGIN_OR_PASSWORD_ERROR);
        }
    }

    private void setButtonInPressedColor() {
        login.setBackgroundColor(GlobalConfig.MainWindowConfig.getButtonPressColor());
    }

    private void setButtonInUpColor() {
        login.setBackgroundColor(GlobalConfig.MainWindowConfig.getFormColor());
    }

    private void onButtonPressed(SharedPreferences proxyInfo) throws ServerDownException,
            TimeoutException, PasswordErrorException, ProxyDetectException {

        if (proxyInfo.getBoolean(MainSettingsActivity.CHECK_BOX_KEY, false)) throw new ProxyDetectException();
        if (Server.isNotConnect()) throw new ServerDownException();
        Server.connect(userName.getText().toString(), password.getText().toString());
        if (Server.passwordIsWrong()) throw new PasswordErrorException();
        startMainMenu();
    }

    private void startMainMenu() {
        context.startActivity(new Intent(context, MenuActivity.class));
    }

    private void startServerWithProxy(SharedPreferences proxyInfo) throws ServerDownException,
            PasswordErrorException, TimeoutException {

        String address = proxyInfo.getString(MainSettingsActivity.IP_KEY, GlobalConstants.EMPTY_STRING);
        int port = Integer.parseInt(proxyInfo.getString(MainSettingsActivity.PORT_KEY, GlobalConstants.EMPTY_STRING));

        if (!Server.isNotConnect(address, port)) throw new ServerDownException();
        Server.connect(userName.getText().toString(), password.getText().toString(), address, port);
        if (Server.passwordIsWrong()) throw new PasswordErrorException();
        startMainMenu();
    }

    private void messageOnScreen(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private class CustomLoginEditText extends EditText {

        public CustomLoginEditText(Context context) {
            super(context);
            super.setLayoutParams(new ViewGroup.LayoutParams(
                    GlobalConfig.MainWindowConfig.getFormWidth(),
                    GlobalConfig.MainWindowConfig.getFormHeight()));
            super.setBackgroundColor(GlobalConfig.MainWindowConfig.getFormColor());
            super.setTextColor(GlobalConfig.MainWindowConfig.getTextColor());
            super.setTextSize(GlobalConstants.DEFAULT_TEXT_SIZE);
            super.setText(GlobalConstants.EMPTY_STRING);
            super.setGravity(Gravity.CENTER_VERTICAL);
            super.setTypeface(Typeface.SERIF);
            super.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
            super.setHintTextColor(GlobalConfig.MainWindowConfig.getTextColor());
        }
    }

    private class ServerDownException extends Throwable {
    }

    private class PasswordErrorException extends Throwable {
    }

    private class ProxyDetectException extends Throwable {
    }
}
