package ru.journal.fspoPrj.login_form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.login_form.elements.MainWindow;
import ru.journal.fspoPrj.login_form.elements.MenuShower;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.server_java.storage.CachedStorage;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.server_info.ServerErrors;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;
import ru.journal.fspoPrj.settings_form.elements.ThemeManager;

public class FirstActivity extends Activity implements View.OnClickListener {

    private MainWindow mainWindow;

    private static final String USER_NAME_KEY = "User name";
    private static final String PASSWORD_KEY = "Password";

    private static boolean currentTheme;
    private static Toast messageShower;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareGlobalPreference();
        mainWindow = new MainWindow(this);
        mainWindow.setOnClickListener(this);
        setContentView(mainWindow);
        startActionMode(new MenuShower(this));
    }

    private void prepareGlobalPreference() {
        currentTheme = getThemeState();
        GlobalConfig.setGlobalPixelDensityInfo(this);
        GlobalConfig.setMatrixTheme(currentTheme);
        GlobalConfig.acceptPreference();
    }

    private void refreshActivity() {
        GlobalConfig.acceptPreference();
        startActivity(new Intent(this, FirstActivity.class));
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mainWindow.getAuthBufferedLink().getRequestCode()) {
            handleAuthorization();
        }
    }

    private void handleAuthorization() {
        if (CachedStorage.isHavingDataFor(mainWindow.getAuthBufferedLink().getQueryLink())) {
            CachedStorage.cachedAuthorizationInfo(mainWindow.getAuthBufferedLink().getQueryLink());
            if (CachedStorage.isTokenValid()) {
                mainWindow.startMainMenu();
            } else {
                showMessage(ServerErrors.LOGIN_OR_PASSWORD_ERROR.message());
                Toast.makeText(this, ServerErrors.LOGIN_OR_PASSWORD_ERROR.message(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showMessage(String message) {
        if (messageShower == null) {
            messageShower = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            messageShower.setText(message);
        }
        messageShower.show();
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
        if (currentTheme != getThemeState()) {
            refreshActivity();
        }
        Server.disconnect();
        mainWindow.loadWindowInfo();
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

    public boolean getThemeState() {
        return getSharedPreferences(MainSettingsActivity.SETTINGS_KEY, MODE_PRIVATE)
                .getBoolean(ThemeManager.MATRIX_CHECK_KEY, false);
    }
}
