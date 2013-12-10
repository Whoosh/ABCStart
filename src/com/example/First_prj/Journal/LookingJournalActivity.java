package com.example.First_prj.Journal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.example.First_prj.FirstActivitySettings.IPAddressForm;
import com.example.First_prj.ForAllCode.*;
import com.example.First_prj.Journal.DateHead.DateSelector;
import com.example.First_prj.Journal.DateHead.GroupSelector;
import com.example.First_prj.Journal.DateHead.LessonSelector;
import com.example.First_prj.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LookingJournalActivity extends Activity implements View.OnClickListener {

    private final String DATA_KEY = "Date";
    private final String INDEX_OF_DATA_KEY = "IndexOfDate";
    private final String SCROLL_KEY = "Scroll Position";
    private final String GROUP_KEY = "Group";


    private ActionMode mActionMode;
    private LinearLayout mainLay;
    private GroupSelector groupSelector;
    private DateSelector dateSelector;
    private LessonSelector lessonSelector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainLay = new LinearLayout(this);

        groupSelector = new GroupSelector(this);
        dateSelector = new DateSelector(this);
        LinearLayout dateGroup = new LinearLayout(this);
        lessonSelector = new LessonSelector(this);

        mainLay.setOrientation(LinearLayout.VERTICAL);

        dateGroup.addView(groupSelector);
        dateGroup.addView(new VerticalLine(this, Color.DKGRAY, 5));
        dateGroup.addView(dateSelector);

        mainLay.addView(dateGroup);
        mainLay.addView(new HorizontalLine(this, Color.DKGRAY, 5));
        mainLay.addView(lessonSelector);
        mainLay.addView(new HorizontalLine(this, Color.DKGRAY, 5));

        setContentView(mainLay);

        lessonSelector.setOnClickListener(this);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            for (int i = 0; i < 100; i++) {
                //@TODO тестовый вариант.
                menu.add("Список предметов");
            }
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveStateOnRotateEvent(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadStateOnRotateEvent(savedInstanceState);
    }

    private void saveStateOnRotateEvent(Bundle outState) {
        // сохранение параметров выбора даты
        outState.putString(DATA_KEY, dateSelector.getSelectedDate());
        outState.putInt(INDEX_OF_DATA_KEY, dateSelector.getIndexOfCurrentSelectedDate());
        outState.putInt(SCROLL_KEY, dateSelector.getOldDatePosition());

        // сохранение параметров выбора группы
        outState.putString(GROUP_KEY, groupSelector.getSelectedGroup());
    }

    private void loadStateOnRotateEvent(Bundle savedInstanceState) {
        // загрузка параметров выбора даты
        dateSelector.setOldSelectedDate(savedInstanceState.getString(DATA_KEY));
        dateSelector.setIndexOfSelectedDate(savedInstanceState.getInt(INDEX_OF_DATA_KEY));
        dateSelector.setOldDatePosition(savedInstanceState.getInt(SCROLL_KEY));
        dateSelector.refresh(); // обновили данные.

        // загрузка параметров выбора группы
        groupSelector.setOldSelectedGroup(savedInstanceState.getString(GROUP_KEY));
        groupSelector.refresh();
    }

    public void contextMenuStarter(View element) {
        registerForContextMenu(element);
        openContextMenu(element);
        unregisterForContextMenu(element);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(lessonSelector)) {
            mActionMode = startActionMode(mActionModeCallback);
            view.setSelected(true);
        }

    }
}

