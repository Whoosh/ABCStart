package ru.journal.fspoPrj.main_menu.elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.ToolKitsManager;

import java.util.*;

public class ListMenu extends LinearLayout implements View.OnDragListener, View.OnLongClickListener, View.OnClickListener {

    public static final String STATE_KEY = "state key";

    private static final int UP_SIDE = -1;
    private static final int DOWN_SIDE = 1;

    private ToolKitsManager toolKits;
    private Context context;

    public ListMenu(Context context, ToolKitsManager toolKits) {
        super(context);
        init(context, toolKits);
        addToolsOnList();
    }

    public ListMenu(ToolKitsManager toolKits, Context context) {
        super(context);
        init(context, toolKits);
    }

    public ListMenu(Context context, ToolKitsManager toolKits, SharedPreferences storage) {
        super(context);
        init(context, toolKits);
        Set<String> toolNames = storage.getStringSet(STATE_KEY, null);
        if (toolNames == null) {
            addToolsOnList();
            return;
        }
        if (isDataValid(toolNames)) {
            int i = 0;
            for (String toolName : toolNames) {
                ItemMenu itemMenu = new ItemMenu(context, toolKits.getTool(toolName), i++);
                setListenersOn(itemMenu);
                addView(itemMenu);
            }
        } else {
            addToolsOnList();
        }
    }


    private void init(Context context, ToolKitsManager toolKits) {
        super.setOrientation(VERTICAL);
        this.toolKits = toolKits;
        this.context = context;
        super.setGravity(Gravity.CENTER_HORIZONTAL);
        super.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setMenuItemsStateBack() {
        for (int i = 0; i < getChildCount(); i++) {
            ItemMenu itemMenu = (ItemMenu) getChildAt(i);
            if (itemMenu.isStatePressed()) {
                itemMenu.setUPState();
            }
        }
    }

    public String[] getRotateState() {
        String[] toolsPositionStates = new String[toolKits.getToolsCount()];
        for (int i = 0; i < toolsPositionStates.length; i++) {
            toolsPositionStates[i] = ((ItemMenu) getChildAt(i)).getName();
        }
        return toolsPositionStates;
    }

    public Set<String> getSavedState() {
        Set<String> result = new LinkedHashSet<>();
        Collections.addAll(result, getRotateState());
        return result;
    }

    private void addToolsOnList() {
        for (int i = 0; i < toolKits.getToolsCount(); i++) {
            ItemMenu itemMenu = new ItemMenu(context, toolKits.getTool(i), i);
            setListenersOn(itemMenu);
            super.addView(itemMenu);
        }
    }

    @Override
    public boolean onDrag(View onDroppedElement, DragEvent event) {
        final int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_ENTERED: {
                swapDraggedElements((ItemMenu) onDroppedElement, (ItemMenu) event.getLocalState());
                return true;
            }
            case DragEvent.ACTION_DRAG_ENDED: {
                ItemMenu element = ((ItemMenu) event.getLocalState());
                element.post(new VisibleSetter(element));
                return true;
            }
            default:
                return true;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
        view.startDrag(null, shadowBuilder, view, 0);
        view.setVisibility(INVISIBLE);
        return false;
    }

    @Override
    public void onClick(View view) {
        ((ItemMenu) view).setDownState();
        ((ItemMenu) view).startFunction();
    }

    private void swapDraggedElements(ItemMenu draggedPositionElement, ItemMenu selectedElement) {
        try {
            int firstChildIndex = draggedPositionElement.getIndexOnScreen();
            int lastChildIndex = selectedElement.getIndexOnScreen();
            if (firstChildIndex > lastChildIndex) {
                for (int i = lastChildIndex; i < firstChildIndex; i++) {
                    moveElement((ItemMenu) getChildAt(i), i, DOWN_SIDE);
                }
            } else {
                for (int i = lastChildIndex; i > firstChildIndex; i--) {
                    moveElement((ItemMenu) getChildAt(i), i, UP_SIDE);
                }
            }
        } catch (Exception ex) {
            Logger.printError(ex, getClass());
        }
    }

    private void moveElement(ItemMenu movingElement, int offsetIndex, int side) {
        removeView(movingElement);
        addView(movingElement, movingElement.getIndexOnScreen() + side);
        movingElement.setIndexOnScreen(movingElement.getIndexOnScreen() + side);
        ((ItemMenu) getChildAt(offsetIndex)).setIndexOnScreen(offsetIndex);
    }

    public void setStateWhenRotate(String[] toolsName) {
        try {
            removeAllViews();
            for (int i = 0; i < toolsName.length; i++) {
                ItemMenu itemMenu = new ItemMenu(context, toolKits.getTool(toolsName[i]), i);
                setListenersOn(itemMenu);
                addView(itemMenu);
            }
        } catch (Exception ex) {
            Logger.printError(ex, getClass());
        }
    }

    // TODO more performance
    private boolean isDataValid(Set<String> restoredToolNames) {
        String[] currentUserToolNames = toolKits.getToolsName();
        if (toolKits.getToolsName().length != restoredToolNames.size()) {
            return false;
        }
        for (String toolName : currentUserToolNames) {
            if (!restoredToolNames.contains(toolName)) {
                return false;
            }
        }
        Arrays.sort(currentUserToolNames);
        for (String toolName : restoredToolNames) {
            if (Arrays.binarySearch(currentUserToolNames, toolName) < 0) {
                return false;
            }
        }
        return true;
    }

    private void setListenersOn(ItemMenu itemMenu) {
        itemMenu.setOnLongClickListener(this);
        itemMenu.setOnClickListener(this);
        itemMenu.setOnDragListener(this);
    }

    private class VisibleSetter implements Runnable {
        private final View element;

        public VisibleSetter(ItemMenu element) {
            this.element = element;
        }

        @Override
        public void run() {
            element.setVisibility(VISIBLE);
        }
    }
}
