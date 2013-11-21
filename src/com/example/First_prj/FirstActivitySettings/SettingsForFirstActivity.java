package com.example.First_prj.FirstActivitySettings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.*;
import com.example.First_prj.ForAllCode.BlueLine;
import com.example.First_prj.ForAllCode.BoldLine;
import com.example.First_prj.ForAllCode.SerifTextView;


public class SettingsForFirstActivity extends Activity {

    private IPForm ipForm;
    private final byte ONE = 1;
    private SharedPreferences keyValueStorage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        editor.putString("IP", ipForm.getAddress());
        editor.putString("Port", ipForm.getPort());
        editor.putBoolean("CheckBoxValue", ipForm.isCheckBoxSet());
        editor.commit();
    }

    private void setOldInfoInToForm() {
        keyValueStorage = getSharedPreferences("proxySettings", MODE_PRIVATE);
        ipForm.loadAddress(keyValueStorage.getString("IP", ""));
        ipForm.loadPort(keyValueStorage.getString("Port", ""));
        ipForm.setCheckBox(keyValueStorage.getBoolean("CheckBoxValue", false));
    }

    private void loadProxyState(Bundle savedInstanceState) {
        ipForm.loadAddress(savedInstanceState.getString("IPAddress"));
        ipForm.loadPort(savedInstanceState.getString("Port"));
        ipForm.setCheckBox(savedInstanceState.getBoolean("ProxyCheckBox"));
    }

    private void saveProxyState(Bundle outState) {
        outState.putString("Port", ipForm.getPort());
        outState.putString("IPAddress", ipForm.getAddress());
        outState.putBoolean("ProxyCheckBox", ipForm.isCheckBoxSet());
    }


    private void addElementInToActivity() {

        LinearLayout scrollableLayout = new LinearLayout(this);
        scrollableLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(this);

        LinearLayout settingsCombinLayout = new LinearLayout(this);
        settingsCombinLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout combLayout = new LinearLayout(this);
        combLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView settingNetworkImg = new ImageView(this);
        settingNetworkImg.setImageResource(android.R.drawable.ic_menu_set_as);

        combLayout.addView(settingNetworkImg);
        combLayout.addView(new SerifTextView(this,"\tНастройки",22));

        combLayout.setGravity(Gravity.CENTER_VERTICAL);

        ipForm = new IPForm(this);

        TextView IPSettingText = new TextView(this);
        IPSettingText.setText("\tнастройка прокси");
        IPSettingText.setTextSize(18);
        IPSettingText.setTypeface(Typeface.SANS_SERIF);

        scrollableLayout.addView(IPSettingText);
        scrollableLayout.addView(new BlueLine(this, (byte) 5));
        scrollableLayout.addView(ipForm);
        scrollableLayout.addView(new BlueLine(this, ONE));

        scrollView.addView(scrollableLayout);

        settingsCombinLayout.addView(combLayout);
        settingsCombinLayout.addView(new BoldLine(this,4));
        settingsCombinLayout.addView(scrollView);

        setContentView(settingsCombinLayout);
    }


}
