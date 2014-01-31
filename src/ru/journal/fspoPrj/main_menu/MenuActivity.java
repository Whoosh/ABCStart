package ru.journal.fspoPrj.main_menu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.main_menu.config.Config;
import ru.journal.fspoPrj.main_menu.elements.ListMenu;
import ru.journal.fspoPrj.main_menu.elements.MenuHead;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.might_info.CurrentRolesInfo;


public class MenuActivity extends Activity {

    private LinearLayout mainLay;
    private ListMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMenu(savedInstanceState);
        initMainLayout();
        setContentView(mainLay);
    }

    @Override
    public void onBackPressed() {
        Server.disconnect(this);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        saveState();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putStringArray(ListMenu.STATE_KEY, menu.getRotateState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        menu.setStateWhenRotate(savedInstanceState.getStringArray(ListMenu.STATE_KEY));
    }

    @Override
    protected void onResume() {
        menu.setMenuItemsStateBack();
        super.onResume();
    }

    private void saveState() {
        SharedPreferences.Editor editor = getSharedPreferences(ListMenu.STATE_KEY, MODE_PRIVATE).edit();
        editor.clear();
        editor.putStringSet(ListMenu.STATE_KEY, menu.getSavedState());
        editor.commit();
    }

    private ScrollView getScrollableMenu() {
        ScrollView scrollViewForFunctionList = new ScrollView(this);
        scrollViewForFunctionList.setVerticalScrollBarEnabled(false);
        scrollViewForFunctionList.addView(menu);
        return scrollViewForFunctionList;
    }

    private void initMainLayout() {
        mainLay = new LinearLayout(this);
        mainLay.setOrientation(LinearLayout.VERTICAL);
        mainLay.addView(new MenuHead(this));
        mainLay.addView(Config.getHeaderLine(this));
        mainLay.addView(getScrollableMenu());
        mainLay.setBackgroundResource(Config.getMenuBackground());
    }

    private void initMenu(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            SharedPreferences storage = getSharedPreferences(ListMenu.STATE_KEY, MODE_PRIVATE);
            if (storage != null) {
                menu = new ListMenu(this, CurrentRolesInfo.getToolsKit(), storage);
            } else {
                menu = new ListMenu(this, CurrentRolesInfo.getToolsKit());
            }
        } else {
            menu = new ListMenu(CurrentRolesInfo.getToolsKit(), this);
        }
    }
}





