package ru.journal.fspoPrj.main_menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.main_menu.config.Config;
import ru.journal.fspoPrj.main_menu.elements.ListMenu;
import ru.journal.fspoPrj.main_menu.elements.MenuHead;
import ru.journal.fspoPrj.server_java.Authorization;
import ru.journal.fspoPrj.server_java.might_info.CurrentRolesInfo;
import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.ToolKitsManager;


public class MenuActivity extends Activity {

    public static final String INTENT_GET_TOOLS_KEY = "intkey";

    private static final String STATE_KEY = "stkey";

    private static ToolKitsManager toolKits;

    private ListMenu listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(INTENT_GET_TOOLS_KEY)) {
            toolKits = (ToolKitsManager) getIntent().getSerializableExtra(INTENT_GET_TOOLS_KEY);
            getIntent().removeExtra(INTENT_GET_TOOLS_KEY);
        }

        if (savedInstanceState == null) {
            listMenu = new ListMenu(this, toolKits);
        } else {
            listMenu = new ListMenu(toolKits, this);
        }
        initMainLayout();
    }

    @Override
    public void onBackPressed() {
        listMenu.storeCollocation();
        new Authorization().disconnect();
        toolKits = null;
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putStringArray(STATE_KEY, listMenu.getRotateState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listMenu.setStateWhenRotate(savedInstanceState.getStringArray(STATE_KEY));
        listMenu.setMenuItemsStateBack();
    }


    private void initMainLayout() {
        ScrollView scrollViewForFunctionList = new ScrollView(this);
        scrollViewForFunctionList.setVerticalScrollBarEnabled(false);
        scrollViewForFunctionList.addView(listMenu);

        LinearLayout mainLay = new LinearLayout(this);
        mainLay.setOrientation(LinearLayout.VERTICAL);
        mainLay.addView(new MenuHead(this));
        mainLay.addView(Config.getHeaderLine(this));
        mainLay.addView(scrollViewForFunctionList);
        mainLay.setBackgroundResource(Config.getMenuBackground());

        setContentView(mainLay);
    }
}





