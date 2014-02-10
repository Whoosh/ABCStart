package ru.journal.fspoPrj.main_menu.elements;

import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.Tools.Tool;
import ru.journal.fspoPrj.server_java.might_info.Tools.ToolKitsManager;

import java.io.*;
import java.util.*;

public class ListMenu extends LinearLayout implements
        View.OnDragListener, View.OnLongClickListener, View.OnClickListener, Iterator<ItemMenu>, Iterable<ItemMenu> {

    private static final String STATE_FILE_NAME = "list collocation";

    private static final int UP_SIDE = -1;
    private static final int DOWN_SIDE = 1;

    private ToolKitsManager toolKits;
    private Context context;
    private int itemCounter;
    private int itemsIndexer;

    public ListMenu(ToolKitsManager toolKits, Context context) {
        super(context);
        init(context, toolKits);
    }

    public ListMenu(Context context, ToolKitsManager toolKits) {
        super(context);
        init(context, toolKits);
        LinkedHashSet<String> toolNames = loadCollocationSet();
        if (!toolNames.isEmpty() && isDataValid(toolNames)) {
            for (String toolName : toolNames) {
                this.add(new ItemMenu(context, toolKits.getTool(toolName), itemCounter++));
            }
        } else {
            for (Tool tool : toolKits.getTools()) {
                this.add(new ItemMenu(context, tool, itemCounter++));
            }
        }
    }

    public void add(ItemMenu itemMenu) {
        try {
            super.addView(itemMenu);
            this.setListenersOn(itemMenu);
        } catch (Exception ignored) {
        }
    }

    public void storeCollocation() {
        try {
            PrintWriter writer = new PrintWriter(context.openFileOutput(STATE_FILE_NAME, Context.MODE_PRIVATE));
            for (ItemMenu item : this) {
                writer.println(item.getName());
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.printError(ex, getClass());
        }
    }

    public void setMenuItemsStateBack() {
        for (ItemMenu item : this) {
            if (item.isStatePressed()) {
                item.setUPState();
            }
        }
    }

    public String[] getRotateState() {
        String[] toolsPositionStates = new String[toolKits.size()];
        for (int i = 0; i < toolsPositionStates.length; i++) {
            toolsPositionStates[i] = getChildAt(i).getName();
        }
        return toolsPositionStates;
    }

    public void setStateWhenRotate(String[] toolsName) {
        itemCounter = 0;
        for (String aToolsName : toolsName) {
            try {
                this.add(new ItemMenu(context, toolKits.getTool(aToolsName), itemCounter++));
            } catch (Exception ex) {
                Logger.printError(ex, getClass());
            }
        }
    }

    private void init(Context context, ToolKitsManager toolKits) {
        super.setOrientation(VERTICAL);
        super.setGravity(Gravity.CENTER_HORIZONTAL);
        super.setBackgroundColor(Color.TRANSPARENT);
        this.itemCounter = 0;
        this.toolKits = toolKits;
        this.context = context;
    }


    private LinkedHashSet<String> loadCollocationSet() {
        LinkedHashSet<String> result = new LinkedHashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(STATE_FILE_NAME)));
            for (String line; (line = reader.readLine()) != null; ) {
                result.add(line);
            }
            reader.close();
        } catch (IOException e) {
            Logger.printError(e, getClass());
            return new LinkedHashSet<>();
        }
        return result;
    }

    private void swapDraggedElements(ItemMenu draggedPositionElement, ItemMenu selectedElement) {
        try {
            int firstChildIndex = draggedPositionElement.getIndexOnScreen();
            int lastChildIndex = selectedElement.getIndexOnScreen();
            if (firstChildIndex > lastChildIndex) {
                for (int i = lastChildIndex; i < firstChildIndex; i++) {
                    moveElement(getChildAt(i), i, DOWN_SIDE);
                }
            } else {
                for (int i = lastChildIndex; i > firstChildIndex; i--) {
                    moveElement(getChildAt(i), i, UP_SIDE);
                }
            }
        } catch (Exception ex) {
            Logger.printError(ex, getClass());
        }
    }

    private void moveElement(ItemMenu movingElement, int offsetIndex, int side) {
        removeView(movingElement);
        addView(movingElement, movingElement.getIndexOnScreen() + side);
        movingElement.changeScreeningIndex(movingElement.getIndexOnScreen() + side);
        getChildAt(offsetIndex).changeScreeningIndex(offsetIndex);
    }

    private boolean isDataValid(Set<String> restoredToolNames) {
        String[] currentUserToolNames = toolKits.getToolsName();
        if (currentUserToolNames.length != restoredToolNames.size()) {
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

    private void clickedOnItem(ItemMenu itemMenu) {
        if (!itemMenu.isStatePressed()) {
            itemMenu.setDownState();
            itemMenu.startFunction();
        }
    }

    @Nullable
    @Override
    public ItemMenu getChildAt(int index) {
        return (ItemMenu) super.getChildAt(index);
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
        clickedOnItem((ItemMenu) view);
    }

    @NotNull
    @Override
    public Iterator<ItemMenu> iterator() {
        this.itemsIndexer = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        return itemsIndexer < itemCounter;
    }

    @Override
    public ItemMenu next() {
        return getChildAt(itemsIndexer++);
    }

    @Override
    public void remove() {
        this.itemCounter--;
        removeView(getChildAt(itemsIndexer));
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
