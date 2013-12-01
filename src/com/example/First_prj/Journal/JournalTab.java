package com.example.First_prj.Journal;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TabHost;
import com.example.First_prj.FirstActivitySettings.LoginFormSettingsActivity;
import com.example.First_prj.R;


public class JournalTab extends TabActivity implements View.OnTouchListener {
    private TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tabjournalsrc);

        tabHost = getTabHost();
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("1");
        tabSpec.setIndicator(new TabViewElement(this, "Просмотр"));
        tabSpec.setContent(new Intent(this, LookingJournalActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("2");
        tabSpec.setIndicator(new TabViewElement(this, "Редактирование"));
        tabSpec.setContent(new Intent(this, EditJournalActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("3");
        tabSpec.setIndicator(new TabViewElement(this, "Новый"));
        tabSpec.setContent(new Intent(this, EditJournalActivity.class));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(2);
        tabHost.onTouchModeChanged(true);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("123");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tabHost.setCurrentTab(0);
        return super.onOptionsItemSelected(item);
    }
}


