package com.example.First_prj.LoginForm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.First_prj.FirstActivitySettings.LoginFormSettingsActivity;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.LiteMatrixDraw;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.ForAllCode.TransparentHorizontalView;
import com.example.First_prj.JavaServer.Server;
import com.example.First_prj.JavaServer.UserInfo;
import com.example.First_prj.MenuAndSwitchers.MenuActivity;

import java.util.concurrent.TimeoutException;

public class MainWindow extends LinearLayout implements View.OnTouchListener {

    public static String MIGHT_CODE_KEY = "MightCode";

    private final String SERVER_IS_DOWN = "Нет соеденения с сервером, попробуйте ещё раз";
    private final String LOGIN_OR_PASSWORD_ERROR = "Неверный логин или пароль";
    private final String SERVER_TTL_QUERY_ERROR = "Сервер не отвечает, проверьте подлючение и попробуйте ещё раз";

    private static final String SETTINGS_KEY = "User Settings";
    private static final String USER_NAME_KEY = "User Name";
    private static final String PASSWORD_KEY = "Password";
    private static final String SAVE_KEY = "Save me";

    private SharedPreferences keyValueStorage;
    private CustomLoginEditText userName;
    private CustomLoginEditText password;
    private Context context;
    private CheckBox saveMe;
    private SerifTextView login;

    public MainWindow(Context context) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        super.setGravity(Gravity.CENTER);
        super.setOrientation(VERTICAL);
        super.setBackgroundDrawable(new LiteMatrixDraw(context));
        LinearLayout buttonBox = new LinearLayout(context);
        float metric = context.getResources().getDisplayMetrics().density;
        this.context = context;

        userName = new CustomLoginEditText(context);

        saveMe = new CheckBox(context);
        saveMe.setTextColor(Color.rgb(140, 140, 140));
        saveMe.setTextSize(Constants.DEFAULT_TEXT_SIZE);
        saveMe.setText("Запомнить ");
        saveMe.setDrawingCacheBackgroundColor(Color.GREEN);

        login = new SerifTextView(context, "Войти", 15);
        login.setLayoutParams(new ViewGroup.LayoutParams((int) (80 * metric), (int) (40 * metric)));
        login.setBackgroundColor(Color.argb(200, 25, 25, 25));
        login.setTextColor(Color.rgb(0, 100, 0));

        password = new CustomLoginEditText(context);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setHint("Пароль");
        password.setTypeface(Typeface.SERIF);
        userName.setHint("Имя пользователя");
        userName.setTypeface(Typeface.SERIF);

        buttonBox.setOrientation(HORIZONTAL);
        buttonBox.setGravity(Gravity.RIGHT);
        buttonBox.setLayoutParams(new ViewGroup.LayoutParams((int) (200 * metric), (int) (40 * metric)));
        buttonBox.addView(saveMe);
        buttonBox.addView(login);

        super.addView(userName);
        super.addView(new TransparentHorizontalView(context, 5));
        super.addView(password);
        super.addView(new TransparentHorizontalView(context, 5));
        super.addView(buttonBox);

        login.setOnTouchListener(this);
    }

    public String getPassword() {
        try {
            return password.getText().toString();
        } catch (NullPointerException ex) {
            return "";
        }
    }

    public String getUserName() {
        try {
            return userName.getText().toString();
        } catch (NullPointerException ex) {
            return "";
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
            userName.setText(keyValueStorage.getString(USER_NAME_KEY, ""));
            password.setText(keyValueStorage.getString(PASSWORD_KEY, ""));
            saveMe.setChecked(true);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.equals(login) && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            login.setBackgroundColor(Color.rgb(0, 255, 0));
            onButtonPressed();
        }
        return true;
    }

    public void restoreVisualElementState() {
        login.setBackgroundColor(Color.argb(200, 25, 25, 25));
    }

    private void onButtonPressed() {
        SharedPreferences proxyInfo = context.getSharedPreferences(LoginFormSettingsActivity.PROXY_KEY, Context.MODE_PRIVATE);
        if (proxyInfo.getBoolean(LoginFormSettingsActivity.CHECK_BOX_KEY, false)) {
            startServerWithProxy(proxyInfo);
            return;
        }
        try {
            if (!Server.isConnect(context)) {
                messageOnScreen(SERVER_IS_DOWN);
                return;
            }
            Server.connect(context, userName.getText().toString(), password.getText().toString());
            if (Server.isPasswordOK()) {
                startMainMenu();
                return;
            }
            messageOnScreen(LOGIN_OR_PASSWORD_ERROR);
        } catch (TimeoutException e) {
            messageOnScreen(SERVER_TTL_QUERY_ERROR);
        }
    }

    private void startMainMenu() {

        Intent intent = new Intent(context, MenuActivity.class);
        //@TODO mightCode = Server.getMightCode(); // код указывающий могущество сущности
        intent.putExtra(MIGHT_CODE_KEY, Constants.TEACHER_CODE);
        context.startActivity(intent);
    }

    private void startServerWithProxy(SharedPreferences proxyInfo) {
        String address = proxyInfo.getString(LoginFormSettingsActivity.IP_KEY, "");
        int port = Integer.parseInt(proxyInfo.getString(LoginFormSettingsActivity.PORT_KEY, ""));
        try {
            if (!Server.isConnect(context, address, port)) {
                messageOnScreen(SERVER_IS_DOWN);
                return;
            }
            Server.connect(context, userName.getText().toString(), password.getText().toString(), address, port);
            if (Server.isPasswordOK()) {
                startMainMenu();
                return;
            }
            messageOnScreen(LOGIN_OR_PASSWORD_ERROR);
        } catch (TimeoutException e) {
            messageOnScreen(SERVER_TTL_QUERY_ERROR);
        }
    }

    private void messageOnScreen(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
