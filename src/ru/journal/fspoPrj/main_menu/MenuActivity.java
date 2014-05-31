package ru.journal.fspoPrj.main_menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationCommunicator;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.main_menu.config.Config;
import ru.journal.fspoPrj.main_menu.elements.BackBar;
import ru.journal.fspoPrj.main_menu.elements.ListMenu;
import ru.journal.fspoPrj.main_menu.elements.MenuHead;
import ru.journal.fspoPrj.server_java.might_info.Tools.ToolKitsManager;


public class MenuActivity extends Activity {

    private static final String STATE_KEY = "stkey";

    private static ToolKitsManager toolKits;

    private ListMenu listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(AuthorizationExecutor.TOOLS_KIT_KEY)) {
            toolKits = (ToolKitsManager) getIntent().getSerializableExtra(AuthorizationExecutor.TOOLS_KIT_KEY);
            getIntent().removeExtra(AuthorizationExecutor.TOOLS_KIT_KEY);
        }
        if (savedInstanceState == null) {
            listMenu = new ListMenu(this, toolKits);
        } else {
            listMenu = new ListMenu(toolKits, this);
        }
        startActionMode(new BackBar(this));
        initMainLayout();
    }

    @Override
    public void onBackPressed() {
        listMenu.storeCollocation();
        new AuthorizationCommunicator().disconnect();
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
    }

    @Override
    protected void onResume() {
        listMenu.setMenuItemsStateBack();
        super.onResume();
    }

    private void initMainLayout() {
        ScrollView scrollViewForFunctionList = new ScrollView(this);
        scrollViewForFunctionList.setVerticalScrollBarEnabled(false);
        scrollViewForFunctionList.addView(listMenu);

        LinearLayout mainLay = new LinearLayout(this);
        mainLay.setOrientation(LinearLayout.VERTICAL);
        //mainLay.addView(new MenuHead(this));
        //mainLay.addView(Config.getHeaderLine(this));
        mainLay.addView(scrollViewForFunctionList);
        mainLay.setBackgroundResource(Config.getMenuBackground());

        setContentView(mainLay);
    }
}





