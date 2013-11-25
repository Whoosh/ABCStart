package com.example.First_prj.FirstActivitySettings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.*;
import com.example.First_prj.ForAllCode.BoldGradientLine;
import com.example.First_prj.ForAllCode.Icon;
import com.example.First_prj.ForAllCode.SerifTextView;


public class LoginFormSettingsActivity extends Activity {

    private IPAddressForm ipAddressForm;
    private SharedPreferences keyValueStorage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addElementInToActivity();
        setOldInfoInToForm();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveProxyState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
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
        keyValueStorage = getSharedPreferences("proxySettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString("IP", ipAddressForm.getAddress());
        editor.putString("Port", ipAddressForm.getPort());
        editor.putBoolean("CheckBoxValue", ipAddressForm.isProxySet());
        editor.commit();
    }

    private void setOldInfoInToForm() {
        keyValueStorage = getSharedPreferences("proxySettings", MODE_PRIVATE);
        ipAddressForm.loadAddress(keyValueStorage.getString("IP", ""));
        ipAddressForm.loadPort(keyValueStorage.getString("Port", ""));
        ipAddressForm.setProxyCheckBoxState(keyValueStorage.getBoolean("CheckBoxValue", false));
    }

    private void loadProxyState(Bundle savedInstanceState) {
        ipAddressForm.loadAddress(savedInstanceState.getString("IPAddress"));
        ipAddressForm.loadPort(savedInstanceState.getString("Port"));
        ipAddressForm.setProxyCheckBoxState(savedInstanceState.getBoolean("ProxyCheckBox"));
    }

    private void saveProxyState(Bundle outState) {
        outState.putString("Port", ipAddressForm.getPort());
        outState.putString("IPAddress", ipAddressForm.getAddress());
        outState.putBoolean("ProxyCheckBox", ipAddressForm.isProxySet());
    }

    private void addElementInToActivity() {

        LinearLayout scrollableLayout = new LinearLayout(this);
        scrollableLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout listOfSettings = new LinearLayout(this);
        listOfSettings.setOrientation(LinearLayout.VERTICAL);

        LinearLayout combLayout = new LinearLayout(this);
        combLayout.setOrientation(LinearLayout.HORIZONTAL);

        ScrollView scrollView = new ScrollView(this);

        combLayout.addView(new Icon(this,android.R.drawable.ic_menu_set_as));
        combLayout.addView(new SerifTextView(this, "\tНастройки", 22));

        ipAddressForm = new IPAddressForm(this);

        scrollableLayout.addView(ipAddressForm);

        scrollView.addView(scrollableLayout);

        listOfSettings.addView(combLayout);
        listOfSettings.addView(new BoldGradientLine(this, 4));
        listOfSettings.addView(scrollView);

        setContentView(listOfSettings);
    }


}
