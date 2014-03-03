package ru.journal.fspoPrj.login_form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationCommunicator;
import ru.journal.fspoPrj.login_form.elements.MainWindow;
import ru.journal.fspoPrj.login_form.elements.MenuShower;
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;
import ru.journal.fspoPrj.settings_form.elements.ThemeManager;

public class FirstActivity extends Activity implements View.OnClickListener, AuthorizationCommunicator.OnAuthCallBack {

    private static int theme;
    private static AuthorizationCommunicator authorizationCommunicator;

    static {
        authorizationCommunicator = new AuthorizationCommunicator();
    }

    private MainWindow mainWindow;
    private MenuShower menuShower;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            theme = getThemeState();
            GlobalConfig.setTheme(theme);
            GlobalConfig.setCurrentThemeFor(this);
            GlobalConfig.setGlobalThemePreference();
            GlobalConfig.prepareGlobalPreference(this);
        } else {
            GlobalConfig.setCurrentThemeFor(this);
            theme = GlobalConfig.getCurrentTheme();
        }
        super.onCreate(savedInstanceState);
        menuShower = new MenuShower(this);
        authorizationCommunicator.setAuthCallBack(this);

        mainWindow = new MainWindow(this, authorizationCommunicator);
        mainWindow.setOnClickListener(this);

        startActionMode(menuShower);
        setContentView(mainWindow);
    }

    private int getThemeState() {
        return getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, MODE_PRIVATE)
                .getInt(ThemeManager.THEME_KEY, MainSettingsActivity.DEFAULT_VALUE);
    }

    @Override
    protected void onResume() {
        if (theme != GlobalConfig.getCurrentTheme()) {
            this.recreate();
        }
        mainWindow.restoreInfo();
        super.onResume();
    }

    @Override
    public void authSuccessful(Intent resource) {
        resource.setClass(this, MenuActivity.class);
        startActivity(resource);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            authorizationCommunicator.handleResponse(data, resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        mainWindow.saveCurrentInfo();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        menuShower.close();
        super.onBackPressed();
    }


    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putString(MainWindow.USER_NAME_KEY, mainWindow.getUserName());
        outState.putString(MainWindow.PASSWORD_KEY, mainWindow.getPassword());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainWindow.setUserName(savedInstanceState.get(MainWindow.USER_NAME_KEY).toString());
        mainWindow.setPassword(savedInstanceState.get(MainWindow.PASSWORD_KEY).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        startActionMode(new MenuShower(this));
        return false;
    }

    @Override
    public void onClick(View view) {
        closeVirtualKeyBoard();
    }

    private void closeVirtualKeyBoard() {
        try {
            InputMethodManager method = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            method.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }
}
