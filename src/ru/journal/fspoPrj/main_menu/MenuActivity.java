package ru.journal.fspoPrj.main_menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import ru.journal.fspoPrj.main_menu.user_factory.ToolsGetter;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.configs.MainMenuConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.server_java.ServerErrors;
import ru.journal.fspoPrj.server_java.might_info.MightInfo;


public class MenuActivity extends Activity {

    private static final String MENU_TITLE = "\tГлавное меню";

    private LinearLayout mainLay;
    private ToolsGetter userTools;
    private LinearLayout functionsList;

    public static final int IS_REQUEST_RESULT_CODE = 440;

    public static boolean SERVER_HAS_CONNECTED_ERROR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        userTools = MightInfo.getToolsKit();
        functionsList = new LinearLayout(this);

        initMainLayout();
        generateFunctionsList();
        addFunctionsListOnMainLayout();

        setContentView(mainLay);
    }

    private void generateFunctionsList() {
        functionsList.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < userTools.getToolsCount(); i++) {
            functionsList.addView(new ItemMenu(this, userTools.getToolName(i), i));
        }
    }

    private void addFunctionsListOnMainLayout() {
        ScrollView scrollViewForFunctionList = new ScrollView(this);
        scrollViewForFunctionList.setVerticalScrollBarEnabled(false);

        scrollViewForFunctionList.addView(functionsList);
        mainLay.addView(scrollViewForFunctionList);
    }

    private void startActivity(int index) {
        startActivityForResult(new Intent(this, userTools.getToolClass(index)), IS_REQUEST_RESULT_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SERVER_HAS_CONNECTED_ERROR && IS_REQUEST_RESULT_CODE == resultCode)
            showMessageOnScreen(ServerErrors.SERVER_TTL_QUERY_ERROR.message());
    }

    private void showMessageOnScreen(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initMainLayout() {
        mainLay = new LinearLayout(this);
        mainLay.setOrientation(LinearLayout.VERTICAL);
        mainLay.setBackgroundColor(MainMenuConfig.getBackgroundColor());
        mainLay.addView(new HeadMenu(this));
        mainLay.addView(GlobalConfig.getHeaderLine(this));
        mainLay.addView(new TransparentHorizontalLine(this, MainMenuConfig.getTransparentViewHeight()));
    }

    private class HeadMenu extends LinearLayout {
        public HeadMenu(Context context) {
            super(context);
            super.addView(new IconSetter(context, android.R.drawable.ic_menu_agenda));
            super.addView(new SerifTextView(context, MENU_TITLE, GlobalConfig.HEADER_TEXT_SIZE));
            super.setBackgroundColor(MainMenuConfig.getMenuElementColor());
        }
    }

    private class ItemMenu extends LinearLayout implements View.OnTouchListener {

        private SerifTextView textView;
        private LinearLayout itemTextIcon;
        private final int selectedElementID;

        public ItemMenu(Context context, String itemText, int selectedElementID) {
            super(context);
            this.selectedElementID = selectedElementID;

            textView = new SerifTextView(context, itemText, GlobalConfig.HEADER_TEXT_SIZE);
            itemTextIcon = new LinearLayout(context);
            itemTextIcon.addView(new IconSetter(context, android.R.drawable.ic_media_play));
            itemTextIcon.addView(textView);

            super.setOnTouchListener(this);

            super.setBackgroundColor(MainMenuConfig.getMenuElementColor());
            super.setOrientation(VERTICAL);
            super.addView(MainMenuConfig.getEndStartLineHorizontalLine(context));
            super.addView(itemTextIcon);
            super.addView(MainMenuConfig.getEndStartLineHorizontalLine(context));
            super.addView(new HorizontalLine(context, MainMenuConfig.getBackgroundColor()
                    , MainMenuConfig.getTransparentViewHeight()));
        }

        private void setBlinkedColor() {
            itemTextIcon.setBackgroundColor(MainMenuConfig.getButtonPressColor());
        }

        private void setBlinkedColorBack() {
            itemTextIcon.setBackgroundColor(MainMenuConfig.getButtonBackColor());
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setBlinkedColor();
            } else {
                setBlinkedColorBack();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                startActivity(selectedElementID);
            }
            return true;
        }
    }
}
