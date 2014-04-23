package ru.journal.fspoPrj.settings_form;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.settings_form.config.Config;
import ru.journal.fspoPrj.settings_form.elements.ProxyManager;
import ru.journal.fspoPrj.settings_form.elements.SettingsHead;
import ru.journal.fspoPrj.settings_form.elements.ThemeManager;


public class MainSettingsActivity extends Activity {

    private static final String EMPTY = "";

    public static final String IP_KEY = "IP";
    public static final String PORT_KEY = "Port";
    public static final String CHECK_BOX_KEY = "CheckBoxValue";
    public static final String SETTINGS_KEY = "Settings";
    public static final int DEFAULT_VALUE = 0;

    private ProxyManager proxyManager;
    private ThemeManager themeManager;
    private SharedPreferences keyValueStorage;

    public void onCreate(Bundle savedInstanceState) {
        GlobalConfig.setCurrentThemeFor(this);
        super.onCreate(savedInstanceState);
        setElements();
        loadOldInformation();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        onRotateSaveProxyState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onRotateLoadProxyState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        saveFormsInfo();
        super.onPause();
    }

    private void saveFormsInfo() {
        keyValueStorage = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);

        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString(IP_KEY, proxyManager.getAddress());
        editor.putString(PORT_KEY, proxyManager.getPort());
        editor.putBoolean(CHECK_BOX_KEY, proxyManager.isProxySet());

        editor.putBoolean(ThemeManager.MATRIX_CHECK_KEY, themeManager.getMatrixCheck());
        editor.putInt(ThemeManager.THEME_KEY, GlobalConfig.getCurrentTheme());

        editor.commit();
    }

    private void loadOldInformation() {
        keyValueStorage = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);

        proxyManager.loadAddress(keyValueStorage.getString(IP_KEY, EMPTY));
        proxyManager.loadPort(keyValueStorage.getString(PORT_KEY, EMPTY));
        proxyManager.setProxyCheckBoxState(keyValueStorage.getBoolean(CHECK_BOX_KEY, false));

        themeManager.setMatrixCheck(keyValueStorage.getBoolean(ThemeManager.MATRIX_CHECK_KEY, false));
        themeManager.setNormalCheck(!themeManager.getMatrixCheck());
        GlobalConfig.setTheme(keyValueStorage.getInt(ThemeManager.THEME_KEY, DEFAULT_VALUE));
    }

    private void onRotateLoadProxyState(Bundle savedInstanceState) {
        proxyManager.loadAddress(savedInstanceState.getString(IP_KEY));
        proxyManager.loadPort(savedInstanceState.getString(PORT_KEY));
        proxyManager.setProxyCheckBoxState(savedInstanceState.getBoolean(CHECK_BOX_KEY));

        themeManager.setMatrixCheck(savedInstanceState.getBoolean(ThemeManager.MATRIX_CHECK_KEY));
        themeManager.setNormalCheck(!themeManager.getMatrixCheck());
    }

    private void onRotateSaveProxyState(Bundle outState) {
        outState.putString(PORT_KEY, proxyManager.getPort());
        outState.putString(IP_KEY, proxyManager.getAddress());
        outState.putBoolean(CHECK_BOX_KEY, proxyManager.isProxySet());

        outState.putBoolean(ThemeManager.MATRIX_CHECK_KEY, themeManager.getMatrixCheck());
    }

    private void setElements() {

        LinearLayout scrollableList = new LinearLayout(this);
        LinearLayout headViewPlusScrollableList = new LinearLayout(this);
        ScrollView scrollView = new ScrollView(this);

        proxyManager = new ProxyManager(this);
        themeManager = new ThemeManager(this);
        themeManager.setVisibility(View.INVISIBLE);

        scrollableList.setOrientation(LinearLayout.VERTICAL);
        headViewPlusScrollableList.setOrientation(LinearLayout.VERTICAL);

        scrollableList.addView(new HorizontalLine(this, Color.TRANSPARENT, Config.getTransparentViewHeight()));
        scrollableList.addView(proxyManager);
        scrollableList.addView(new HorizontalLine(this, Color.TRANSPARENT, Config.getTransparentViewHeight()));
        scrollableList.addView(themeManager);

        scrollView.addView(scrollableList);

        headViewPlusScrollableList.addView(new SettingsHead(this));
        headViewPlusScrollableList.addView(Config.getHeaderLine(this));
        headViewPlusScrollableList.addView(scrollView);

        setContentView(headViewPlusScrollableList);
    }
}
