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
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.server_java.Authorization;
import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.ToolKitsManager;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;
import ru.journal.fspoPrj.settings_form.elements.ThemeManager;

public class FirstActivity extends Activity implements View.OnClickListener, Authorization.OnAuthCallBack {

    private MainWindow mainWindow;
    private static boolean themeTrigger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            GlobalConfig.prepareGlobalPreference(this);  // TODO
        }
        lookAtTheme();

        Authorization authorization = new Authorization(this);

        mainWindow = new MainWindow(this, authorization);
        mainWindow.setOnClickListener(this);

        setContentView(mainWindow);
        startActionMode(new MenuShower(this));
    }

    private void lookAtTheme() {
        themeTrigger = getThemeState();
        GlobalConfig.setMatrixTheme(themeTrigger);
        GlobalConfig.changeThemePreference();
        GlobalConfig.refreshToCurrentTheme(this);
    }

    private boolean getThemeState() {
        return getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, MODE_PRIVATE).getBoolean(ThemeManager.MATRIX_CHECK_KEY, false);
    }

    private void refreshActivityTheme() {
        GlobalConfig.changeThemePreference();
        this.recreate();
    }

    @Override
    public void authSuccessful(ToolKitsManager toolKits) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(MenuActivity.INTENT_GET_TOOLS_KEY, toolKits);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        mainWindow.saveCurrentInfo();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        System.exit(0); // TODO fking emulator
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if (themeTrigger != getThemeState()) {
            refreshActivityTheme();
        }
        mainWindow.restoreInfo();
        super.onResume();
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
        try {// закрываем виртуальную клавиатуру по клику на пустое место.
            InputMethodManager method = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            method.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }
}
