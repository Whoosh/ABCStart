package com.example.First_prj.FirstActivitySettings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import com.example.First_prj.ForAllCode.DesigneElements.IconSetter;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.LeftToRightHorizontalBoldGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.GlobalConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class LoginFormSettingsActivity extends Activity {

    public static final String IP_KEY = "IP";
    public static final String PORT_KEY = "Port";
    public static final String PROXY_KEY = "Proxy";
    public static final String CHECK_BOX_KEY = "CheckBoxValue";

    private static final String SETTINGS_TITLE = "\tНастройки";

    private IPAddressForm ipAddressForm;
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
        keyValueStorage = getSharedPreferences(PROXY_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString(IP_KEY, ipAddressForm.getAddress());
        editor.putString(PORT_KEY, ipAddressForm.getPort());
        editor.putBoolean(CHECK_BOX_KEY, ipAddressForm.isProxySet());
        editor.commit();
    }

    private void setOldInfoInToForm() {
        keyValueStorage = getSharedPreferences(PROXY_KEY, MODE_PRIVATE);
        ipAddressForm.loadAddress(keyValueStorage.getString(IP_KEY, GlobalConstants.EMPTY_STRING));
        ipAddressForm.loadPort(keyValueStorage.getString(PORT_KEY, GlobalConstants.EMPTY_STRING));
        ipAddressForm.setProxyCheckBoxState(keyValueStorage.getBoolean(CHECK_BOX_KEY, false));
    }

    private void loadProxyState(Bundle savedInstanceState) {
        ipAddressForm.loadAddress(savedInstanceState.getString(IP_KEY));
        ipAddressForm.loadPort(savedInstanceState.getString(PORT_KEY));
        ipAddressForm.setProxyCheckBoxState(savedInstanceState.getBoolean(CHECK_BOX_KEY));
    }

    private void saveProxyState(Bundle outState) {
        outState.putString(PORT_KEY, ipAddressForm.getPort());
        outState.putString(IP_KEY, ipAddressForm.getAddress());
        outState.putBoolean(CHECK_BOX_KEY, ipAddressForm.isProxySet());
    }

    private void addElementInToActivity() {

        LinearLayout scrollableListForSettings = new LinearLayout(this);
        LinearLayout headViewPlusScrollableListOfSettings = new LinearLayout(this);
        LinearLayout headNonScrollElements = new LinearLayout(this);
        ScrollView scrollPackageForSettingsList = new ScrollView(this);
        ipAddressForm = new IPAddressForm(this);

        scrollableListForSettings.setOrientation(LinearLayout.VERTICAL);
        headViewPlusScrollableListOfSettings.setOrientation(LinearLayout.VERTICAL);

        headNonScrollElements.addView(new IconSetter(this, android.R.drawable.ic_menu_set_as));
        headNonScrollElements.addView(new SerifTextView(this, SETTINGS_TITLE, GlobalConstants.HEADER_TEXT_SIZE));

        scrollableListForSettings.addView(ipAddressForm);

        scrollPackageForSettingsList.addView(scrollableListForSettings);

        headViewPlusScrollableListOfSettings.addView(headNonScrollElements);
        headViewPlusScrollableListOfSettings.addView(new LeftToRightHorizontalBoldGradientLine(this));

        headViewPlusScrollableListOfSettings.addView(scrollPackageForSettingsList);

        setContentView(headViewPlusScrollableListOfSettings);
    }


}
