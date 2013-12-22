package com.example.First_prj.FirstActivitySettings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.DesigneElements.IconSetter;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.GlobalConfig;
import org.jetbrains.annotations.NotNull;

import static com.example.First_prj.ForAllCode.GlobalConfig.MainSettingsConfig.getBackgroundColor;
import static com.example.First_prj.ForAllCode.GlobalConfig.MainSettingsConfig.getFormBackgroundColor;


public class MainSettingsActivity extends Activity {

    public static final String IP_KEY = "IP";
    public static final String PORT_KEY = "Port";
    public static final String CHECK_BOX_KEY = "CheckBoxValue";

    public static final String SETTINGS_KEY = "Settings";

    private static final String SETTINGS_TITLE = "\tНастройки";

    private ProxyManager proxyManager;
    private ThemeManager themeManager;
    private SharedPreferences keyValueStorage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        addElementInToActivity();
        setOldInfoInToForm();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveProxyState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadProxyState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        saveFormsInfo();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void saveFormsInfo() {
        keyValueStorage = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);

        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString(IP_KEY, proxyManager.getAddress());
        editor.putString(PORT_KEY, proxyManager.getPort());
        editor.putBoolean(CHECK_BOX_KEY, proxyManager.isProxySet());

        editor.putBoolean(ThemeManager.MATRIX_CHECK_KEY, themeManager.getMatrixCheck());

        editor.commit();
    }

    private void setOldInfoInToForm() {
        keyValueStorage = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);
        proxyManager.loadAddress(keyValueStorage.getString(IP_KEY, GlobalConfig.EMPTY_STRING));
        proxyManager.loadPort(keyValueStorage.getString(PORT_KEY, GlobalConfig.EMPTY_STRING));
        proxyManager.setProxyCheckBoxState(keyValueStorage.getBoolean(CHECK_BOX_KEY, false));

        themeManager.setMatrixCheck(keyValueStorage.getBoolean(ThemeManager.MATRIX_CHECK_KEY, false));
        themeManager.setNormalCheck(!themeManager.getMatrixCheck());

    }

    private void loadProxyState(Bundle savedInstanceState) {
        proxyManager.loadAddress(savedInstanceState.getString(IP_KEY));
        proxyManager.loadPort(savedInstanceState.getString(PORT_KEY));
        proxyManager.setProxyCheckBoxState(savedInstanceState.getBoolean(CHECK_BOX_KEY));

        themeManager.setMatrixCheck(savedInstanceState.getBoolean(ThemeManager.MATRIX_CHECK_KEY));
        themeManager.setNormalCheck(!themeManager.getMatrixCheck());
    }

    private void saveProxyState(Bundle outState) {
        outState.putString(PORT_KEY, proxyManager.getPort());
        outState.putString(IP_KEY, proxyManager.getAddress());
        outState.putBoolean(CHECK_BOX_KEY, proxyManager.isProxySet());

        outState.putBoolean(ThemeManager.MATRIX_CHECK_KEY, themeManager.getMatrixCheck());
    }

    private void addElementInToActivity() {

        LinearLayout scrollableListForSettings = new LinearLayout(this);
        LinearLayout headViewPlusScrollableListOfSettings = new LinearLayout(this);
        LinearLayout headNonScrollElements = new LinearLayout(this);
        ScrollView scrollPackageForSettingsList = new ScrollView(this);

        proxyManager = new ProxyManager(this);
        themeManager = new ThemeManager(this);

        scrollableListForSettings.setOrientation(LinearLayout.VERTICAL);
        headViewPlusScrollableListOfSettings.setOrientation(LinearLayout.VERTICAL);

        headNonScrollElements.addView(new IconSetter(this, android.R.drawable.ic_menu_set_as));
        headNonScrollElements.addView(new SerifTextView(this, SETTINGS_TITLE, GlobalConfig.HEADER_TEXT_SIZE));
        headNonScrollElements.setBackgroundColor(getFormBackgroundColor());

        scrollableListForSettings.addView(proxyManager);
        scrollableListForSettings.addView(themeManager);
        scrollableListForSettings.setBackgroundColor(getFormBackgroundColor());

        scrollPackageForSettingsList.addView(scrollableListForSettings);

        headViewPlusScrollableListOfSettings.addView(headNonScrollElements);
        headViewPlusScrollableListOfSettings.addView(GlobalConfig.getHeaderLine(this));
        headViewPlusScrollableListOfSettings.addView(scrollPackageForSettingsList);
        headViewPlusScrollableListOfSettings.setBackgroundColor(getBackgroundColor());

        setContentView(headViewPlusScrollableListOfSettings);
    }
}
