package ru.journal.fspoPrj.login_form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.login_form.elements.MainWindow;
import ru.journal.fspoPrj.login_form.elements.MenuShower;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;
import ru.journal.fspoPrj.settings_form.elements.ThemeManager;

public class FirstActivity extends Activity implements View.OnClickListener {

    private MainWindow mainWindow;

    private static final String USER_NAME_KEY = "ToolKitsManager name";
    private static final String PASSWORD_KEY = "Password";

    private static boolean currentTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            GlobalConfig.prepareGlobalPreference(this);
        }
        lookAtTheme();
        mainWindow = new MainWindow(this);
        mainWindow.setOnClickListener(this);
        setContentView(mainWindow);
        startActionMode(new MenuShower(this));
    }

    private void lookAtTheme() {
        currentTheme = getThemeState();
        GlobalConfig.setMatrixTheme(currentTheme);
        GlobalConfig.changeThemePreference();
        GlobalConfig.refreshToCurrentTheme(this);
    }

    private void refreshActivityTheme() {
        GlobalConfig.changeThemePreference();
        this.recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Server.haveErrorsWhenExecutingQuery()) return;
        if (requestCode == mainWindow.getAuthBufferedLink().getRequestCode()) {
            mainWindow.startMainMenu();
        }
    }

    private boolean getThemeState() {
        return getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, MODE_PRIVATE).getBoolean(ThemeManager.MATRIX_CHECK_KEY, false);
    }

    @Override
    protected void onPause() {
        mainWindow.saveCurrentInfo();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (currentTheme != getThemeState()) {
            refreshActivityTheme();
        }
        mainWindow.loadOldInfoOnScreen();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putString(USER_NAME_KEY, mainWindow.getUserName());
        outState.putString(PASSWORD_KEY, mainWindow.getPassword());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainWindow.setUserName(savedInstanceState.get(USER_NAME_KEY).toString());
        mainWindow.setPassword(savedInstanceState.get(PASSWORD_KEY).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        startActionMode(new MenuShower(this));
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mainWindow)) {
            try {// закрываем виртуальную клавиатуру по клику на пустое место.
                InputMethodManager method = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                method.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (NullPointerException ex) {
                Logger.printError(ex, getClass());
            }
        }
    }


}
