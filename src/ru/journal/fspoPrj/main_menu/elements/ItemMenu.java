package ru.journal.fspoPrj.main_menu.elements;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.main_menu.config.Config;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.Tool;

public class ItemMenu extends LinearLayout {

    private boolean inPressedState;
    private int indexOnScreen;
    private Context context;
    private final Tool itemMenuTool;
    private LinearLayout nonColorElement;

    public ItemMenu(Context context, Tool itemMenuTool, int indexOnScreen) {
        super(context);
        this.context = context;
        this.itemMenuTool = itemMenuTool;
        this.indexOnScreen = indexOnScreen;
        initElement();
    }

    public void startFunction() {
        context.startActivity(new Intent(context, itemMenuTool.getToolClass()));
    }

    public void setUPState() {
        inPressedState = false;
        nonColorElement.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setDownState() {
        inPressedState = true;
        nonColorElement.setBackgroundColor(Config.getButtonPressStateColor());
    }

    public boolean isStatePressed() {
        return inPressedState;
    }

    public int getIndexOnScreen() {
        return indexOnScreen;
    }

    public void changeScreeningIndex(int indexOnScreen) {
        this.indexOnScreen = indexOnScreen;
    }

    public String getName() {
        return itemMenuTool.getName();
    }

    private void initElement() {
        LinearLayout coloredElement = new LinearLayout(context);
        LinearLayout combElements = new LinearLayout(context);

        nonColorElement = new LinearLayout(context);
        nonColorElement.setGravity(Gravity.CENTER);
        nonColorElement.addView(new IconSetter(context, itemMenuTool.getIconID()));
        nonColorElement.setLayoutParams(Config.getMenuItemParam());
        nonColorElement.addView(coloredElement);

        coloredElement.setBackgroundResource(Config.getItemMenuBackgroundColor());
        coloredElement.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        coloredElement.setGravity(Gravity.CENTER_VERTICAL);
        coloredElement.addView(new VerticalLine(context, Color.TRANSPARENT, Config.getTextTabWidth()));
        coloredElement.addView(new SerifTextView(context, itemMenuTool.getName(), Config.getItemMenuTextSize()));

        combElements.setOrientation(VERTICAL);
        combElements.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        combElements.addView(Config.getItemMenuStartEndLine(context));
        combElements.addView(nonColorElement);
        combElements.addView(Config.getItemMenuStartEndLine(context));

        super.addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getItemMenuSeparateLineHeight()));
        super.setOrientation(VERTICAL);
        super.setGravity(Gravity.CENTER);
        super.addView(combElements);
    }

    @Override
    public boolean equals(Object element) {
        return ((ItemMenu) element).getName().equals(this.getName());
    }

}