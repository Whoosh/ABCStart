package com.example.First_prj.FirstActivitySettings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import com.example.First_prj.ForAllCode.Gradients.BoldHorizontalGradientLine;
import com.example.First_prj.ForAllCode.Icon;
import com.example.First_prj.ForAllCode.SerifTextView;


public class LoginFormSettingsActivity extends Activity {

    public static String IP_KEY = "IP";
    public static String PORT_KEY = "Port";
    public static String PROXY_KEY = "Proxy";
    public static String CHECK_BOX_KEY = "CheckBoxValue";

    private IPAddressForm ipAddressForm;
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
        keyValueStorage = getSharedPreferences(PROXY_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString(IP_KEY, ipAddressForm.getAddress());
        editor.putString(PORT_KEY, ipAddressForm.getPort());
        editor.putBoolean(CHECK_BOX_KEY, ipAddressForm.isProxySet());
        editor.commit();
    }

    private void setOldInfoInToForm() {
        keyValueStorage = getSharedPreferences(PROXY_KEY, MODE_PRIVATE);
        ipAddressForm.loadAddress(keyValueStorage.getString(IP_KEY, ""));
        ipAddressForm.loadPort(keyValueStorage.getString(PORT_KEY, ""));
        ipAddressForm.setProxyCheckBoxState(keyValueStorage.getBoolean(CHECK_BOX_KEY, false));
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

        //@TODO стоит ли ?
        //listOfSettings.setBackgroundDrawable(new LiteMatrixDraw(this));

        LinearLayout combLayout = new LinearLayout(this);
        combLayout.setOrientation(LinearLayout.HORIZONTAL);

        ScrollView scrollView = new ScrollView(this);

        combLayout.addView(new Icon(this,android.R.drawable.ic_menu_set_as));
        combLayout.addView(new SerifTextView(this, "\tНастройки", 22));

        ipAddressForm = new IPAddressForm(this);

        scrollableLayout.addView(ipAddressForm);

        scrollView.addView(scrollableLayout);

        listOfSettings.addView(combLayout);
        listOfSettings.addView(new BoldHorizontalGradientLine(this, 4));
        listOfSettings.addView(scrollView);

        setContentView(listOfSettings);
    }


}
