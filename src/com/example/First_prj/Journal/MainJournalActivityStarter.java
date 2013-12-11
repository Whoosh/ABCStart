package com.example.First_prj.Journal;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TabHost;
import com.example.First_prj.ForAllCode.TransparentHorizontalView;
import com.example.First_prj.R;


public class MainJournalActivityStarter extends TabActivity {

    private final String LOOKING_TAB = "Просмотр";
    private final String EDIT_TAB = "Редактирование";
    private final String CHECK_STUDENT = "Отметить студентов";
    private final String SOME_MORE = "Что то ещё";

    private final byte LOOKING_TAB_ID = 0;
    private final byte EDIT_TAB_ID = 1;
    private final byte CHECK_STUDENT_ID = 2;
    private final byte SOME_MORE_ID = 3;


    private TabHost tabsSwitcher;
    private ActionMode tabSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tabjournalsrc);
        initTabs();
        tabSelector = startActionMode(selectorItems);
    }


    private ActionMode.Callback selectorItems = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add(0, LOOKING_TAB_ID, 0, LOOKING_TAB);
            menu.add(0, EDIT_TAB_ID, 0, EDIT_TAB);
            menu.add(0, CHECK_STUDENT_ID, 0, CHECK_STUDENT);
            menu.add(0, SOME_MORE_ID, 0, SOME_MORE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case LOOKING_TAB_ID:
                    tabsSwitcher.setCurrentTab(LOOKING_TAB_ID);
                    break;
                case EDIT_TAB_ID:
                    tabsSwitcher.setCurrentTab(EDIT_TAB_ID);
                    break;
                case CHECK_STUDENT_ID:
                    tabsSwitcher.setCurrentTab(CHECK_STUDENT_ID);
                    break;
                case SOME_MORE_ID:
                    tabsSwitcher.setCurrentTab(SOME_MORE_ID);
                    break;
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            tabSelector = null;
            mode.finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tabSelector == null) {
            tabSelector = startActionMode(selectorItems);
        } else {
            tabSelector.finish();
            tabSelector = null;
        }
        return false;
    }

    private void initTabs() {
        tabsSwitcher = getTabHost();
        TabHost.TabSpec tabsElement;

        tabsElement = tabsSwitcher.newTabSpec(LOOKING_TAB);
        tabsElement.setIndicator(new TransparentHorizontalView(this, 0));
        tabsElement.setContent(new Intent(this, LookingJournalActivity.class));
        tabsSwitcher.addTab(tabsElement);

        tabsElement = tabsSwitcher.newTabSpec(EDIT_TAB);
        tabsElement.setIndicator(new TransparentHorizontalView(this, 0));
        //TODO
        tabsElement.setContent(new Intent(this, EditJournalActivity.class));
        tabsSwitcher.addTab(tabsElement);

        tabsElement = tabsSwitcher.newTabSpec(CHECK_STUDENT);
        tabsElement.setIndicator(new TransparentHorizontalView(this, 0));
        //TODO
        tabsElement.setContent(new Intent(this, StudentCheckerActivity.class));
        tabsSwitcher.addTab(tabsElement);

        tabsElement = tabsSwitcher.newTabSpec(SOME_MORE);
        tabsElement.setIndicator(new TransparentHorizontalView(this, 0));
        //TODO
        tabsElement.setContent(new Intent(this, LookingJournalActivity.class));
        tabsSwitcher.addTab(tabsElement);

    }

}


