package com.example.First_prj.Journal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.HorizontalLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.Journal.DateHead.DateSelector;
import com.example.First_prj.Journal.DateHead.GroupSelector;
import com.example.First_prj.Journal.DateHead.LessonSelector;
import com.example.First_prj.Journal.MainTable.DateList;
import com.example.First_prj.Journal.MainTable.StudentList;
import com.example.First_prj.Journal.MainTable.TableWithMarks;

import static com.example.First_prj.ForAllCode.GlobalConfig.LookingJournalConfig.*;

//
public class LookingJournalActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    private final String DATA_KEY = "Date";
    private final String INDEX_OF_DATA_KEY = "IndexOfDate";
    private final String SCROLL_KEY = "Scroll Position";
    private final String GROUP_KEY = "Group";

    private ActionMode mActionMode;
    private LinearLayout mainLay;
    private GroupSelector groupSelector;
    private DateSelector dateSelector;
    private LessonSelector lessonSelector;
    private DateList dateList; // TODO
    private StudentList studentList; // TODO
    private TableWithMarks tableWithMarks; // TODO

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initElements();
        setContentView(mainLay);

        lessonSelector.setOnClickListener(this);
    }

    private void initElements() {
        mainLay = new LinearLayout(this);
        LinearLayout datePlusGroup = new LinearLayout(this);
        LinearLayout dateListPlusLessonSelector = new LinearLayout(this);
        LinearLayout studentsPlusTableLayout = new LinearLayout(this);
        ScrollView studentsPlusTableView = new ScrollView(this);
        studentsPlusTableView.requestDisallowInterceptTouchEvent(false);

        groupSelector = new GroupSelector(this);
        dateSelector = new DateSelector(this);
        lessonSelector = new LessonSelector(this);
        dateList = new DateList(this, 10); // @TODO
        studentList = new StudentList(this);
        tableWithMarks = new TableWithMarks(this, 10, 10); // TODO

        tableWithMarks.requestDisallowInterceptTouchEvent(false);


        studentList.addStudents(10);

        mainLay.setOrientation(LinearLayout.VERTICAL);

        studentsPlusTableLayout.addView(studentList);
        studentsPlusTableLayout.addView(new VerticalLine(this, Color.CYAN));
        studentsPlusTableLayout.addView(tableWithMarks);

        studentsPlusTableView.addView(studentsPlusTableLayout);

        dateListPlusLessonSelector.addView(lessonSelector);
        dateListPlusLessonSelector.addView(new VerticalLine(this, Color.CYAN));
        dateListPlusLessonSelector.addView(dateList);

        datePlusGroup.addView(groupSelector);
        datePlusGroup.addView(new VerticalLine(this, Color.CYAN));
        datePlusGroup.addView(dateSelector);

        mainLay.setBackgroundDrawable(getBackground(this));

        mainLay.addView(datePlusGroup);
        mainLay.addView(new HorizontalLine(this, Color.CYAN));
        mainLay.addView(dateListPlusLessonSelector);
        mainLay.addView(new HorizontalLine(this, Color.CYAN));
        mainLay.addView(studentsPlusTableView);

        dateList.setOnTouchListener(this);
        tableWithMarks.setOnTouchListener(this);
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
        dateSelector.refreshFocusAndState(); // обновили данные.

        // загрузка параметров выбора группы
        groupSelector.setOldSelectedGroup(savedInstanceState.getString(GROUP_KEY));
        groupSelector.refreshStateOfVisualPosition();
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (view.equals(tableWithMarks))
                dateList.scrollTo(tableWithMarks.getScrollX(), 0);
            if (view.equals(dateList))
                tableWithMarks.scrollTo(dateList.getScrollX(), 0);
            return false;
        }
        tableWithMarks.scrollTo(dateList.getScrollX(), 0);
        dateList.scrollTo(tableWithMarks.getScrollX(), 0);

        return true;
    }
}

