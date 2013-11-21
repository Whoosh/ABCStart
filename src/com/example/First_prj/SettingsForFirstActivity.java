package com.example.First_prj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

//@TODO сохранение и востановление при повороте.
public class SettingsForFirstActivity extends Activity {

    IPEditText ipaddresform;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CONTEXT_MENU);

        ipaddresform = new IPEditText(getApplicationContext());

        if (savedInstanceState != null) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            System.out.println(preferences.getString("123", null));
        }
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        setContentView(layout, params);

        System.out.println("onCreate");
        layout.addView(ipaddresform);
    }

    @Override
    protected void onPause() {
        System.out.println("onPaused");
        super.onPause();
        SharedPreferences buffer = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = buffer.edit();
        editor.putString("123", "11111");
        editor.commit();
    }

    @Override
    protected void onResume() {
        System.out.println("onResume");
        super.onResume();
    }

    private void setIntentResult() {
        Intent intent = new Intent();
        intent.putExtra("IP", "1.1.1.1");
        setResult(RESULT_OK, intent);
        finish();
    }
}
