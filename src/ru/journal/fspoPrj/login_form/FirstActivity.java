package ru.journal.fspoPrj.login_form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import ru.journal.fspoPrj.login_form.settings_form.MainSettingsActivity;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.server_java.Server;

public class FirstActivity extends Activity implements View.OnClickListener {

    private MainWindow mainWindow;
    private ActionMode settings;

    private static final String USER_NAME_KEY = "User name";
    private static final String PASSWORD_KEY = "Password";


    private static final String SETTINGS_TITLE = "Настройки";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            GlobalConfig.setGlobalPixelDensityInfo(this);
            GlobalConfig.setThemeConfig(getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, MODE_PRIVATE));
            GlobalConfig.setDefaultSettings();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mainWindow = new MainWindow(this);
        mainWindow.setOnClickListener(this);
        setContentView(mainWindow);

        settings = startActionMode(settingsBar);
    }

    @Override
    protected void onPause() {
        mainWindow.saveWindowInfo();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Server.disconnect();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Server.disconnect();
        mainWindow.loadWindowInfo();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(USER_NAME_KEY, mainWindow.getUserName());
        outState.putString(PASSWORD_KEY, mainWindow.getPassword());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainWindow.setUserName(savedInstanceState.get(USER_NAME_KEY).toString());
        mainWindow.setPassword(savedInstanceState.get(PASSWORD_KEY).toString());
    }

    private ActionMode.Callback settingsBar = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add(SETTINGS_TITLE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.finish();
            startActivity(new Intent(getApplicationContext(), MainSettingsActivity.class));
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            settings = null;
            mode.finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (settings == null) {
            settings = startActionMode(settingsBar);
        } else {
            settings.finish();
            settings = null;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mainWindow)) {
            try {// закрываем виртуальную клавиатуру по клику на пустое место.
                ((InputMethodManager)
                        getSystemService(INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(getCurrentFocus().
                                getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }

    }
}
