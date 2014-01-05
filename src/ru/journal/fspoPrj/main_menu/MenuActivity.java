package ru.journal.fspoPrj.main_menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import ru.journal.fspoPrj.main_menu.config.Config;
import ru.journal.fspoPrj.main_menu.elements.MenuHead;
import ru.journal.fspoPrj.main_menu.user_factory.ToolsGetter;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.server_java.ServerErrors;
import ru.journal.fspoPrj.server_java.might_info.MightInfo;


public class MenuActivity extends Activity {

    private LinearLayout mainLay;
    private ToolsGetter userTools;
    private LinearLayout functionsList;

    public static final int SERVER_CONNECTION_DIE = 408;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userTools = MightInfo.getToolsKit();

        functionsList = new LinearLayout(this);
        functionsList.setOrientation(LinearLayout.VERTICAL);

        initMainLayout();
        generateFunctionsList();
        addFunctionsListOnMainLayout();
        setContentView(mainLay);
    }

    private void generateFunctionsList() {
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
        startActivityForResult(new Intent(this, userTools.getToolClass(index)), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SERVER_CONNECTION_DIE) showMessageOnScreen(ServerErrors.SERVER_TTL_QUERY_ERROR.message());
    }

    private void showMessageOnScreen(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initMainLayout() {
        mainLay = new LinearLayout(this);
        mainLay.setOrientation(LinearLayout.VERTICAL);
        mainLay.setBackgroundColor(Config.getBackgroundColor());
        mainLay.addView(new MenuHead(this));
        mainLay.addView(Config.getHeaderLine(this));
        mainLay.addView(new TransparentHorizontalLine(this, Config.getVoidLineHeight()));
    }

    private class ItemMenu extends LinearLayout implements View.OnTouchListener {

        private SerifTextView textView;
        private LinearLayout itemTextIcon;
        private final int selectedElementID;

        public ItemMenu(Context context, String itemText, int selectedElementID) {
            super(context);
            super.setOnTouchListener(this);
            this.selectedElementID = selectedElementID;

            textView = new SerifTextView(context, itemText, GlobalConfig.HEADER_TEXT_SIZE);
            itemTextIcon = new LinearLayout(context);
            itemTextIcon.addView(new IconSetter(context, android.R.drawable.ic_media_play));
            itemTextIcon.addView(textView);

            super.setBackgroundColor(Config.getMenuElementColor());
            super.setOrientation(VERTICAL);
            super.addView(Config.getEndStartLineHorizontalLine(context));
            super.addView(itemTextIcon);
            super.addView(Config.getEndStartLineHorizontalLine(context));
            super.addView(Config.getSeparateLine(context));
        }

        private void setBlinkedColor() {
            itemTextIcon.setBackgroundColor(Config.getButtonPressColor());
        }

        private void setBlinkedColorBack() {
            itemTextIcon.setBackgroundColor(Config.getButtonBackColor());
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) setBlinkedColor();
            else setBlinkedColorBack();
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) startActivity(selectedElementID);
            return true;
        }
    }
}
