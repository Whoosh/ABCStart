package ru.journal.fspoPrj.journal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.callbacks.Lessons;
import ru.journal.fspoPrj.journal.callbacks.Switcher;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.elements.head_selector.DateSelector;
import ru.journal.fspoPrj.journal.elements.head_selector.GroupSelector;
import ru.journal.fspoPrj.journal.elements.head_selector.LessonSelector;
import ru.journal.fspoPrj.journal.elements.main_table.DateList;
import ru.journal.fspoPrj.journal.elements.main_table.StudentList;
import ru.journal.fspoPrj.journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class LookingJournalActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private static final String DATE_KEY = "IndexOfDate";
    private static final String GROUP_KEY = "Group";

    private GroupSelector groupSelector;
    private DateSelector dateSelector;
    private DateList dateList;
    private TableWithMarks tableWithMarks;

    private LinearLayout mainLay;

    public static ArrayList<String> listOfLessonsNames;
    public static LessonSelector lessonSelector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initElements();
        setContentView(mainLay);
        lessonSelector.setOnClickListener(this);
        startActionMode(new Switcher(this));
    }

    @Override
    protected void onDestroy() {
        setAllStaticFieldsIsNull();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        saveStateOnRotateEvent(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadStateOnRotateEvent(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        startActionMode(new Switcher(this));
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

    @Override
    public void onClick(View view) {
        if (view.equals(lessonSelector)) startActionMode(new Lessons(new ArrayList<String>()));
    }

    private void saveStateOnRotateEvent(Bundle outState) {
        outState.putInt(DATE_KEY, dateSelector.getIndexOfSelectedDate());
        outState.putByte(GROUP_KEY, groupSelector.getOldFocusedIndex());
    }

    private void loadStateOnRotateEvent(Bundle savedInstanceState) {
        dateSelector.setIndexOfSelectedDate(savedInstanceState.getInt(DATE_KEY));
        dateSelector.refreshVisualState();

        groupSelector.setOldFocusedIndex(savedInstanceState.getByte(GROUP_KEY));
        groupSelector.refreshVisualState();
    }


    public static void setAllStaticFieldsIsNull() {
        lessonSelector = null;
        listOfLessonsNames = null;
    }

    private void initElements() {
        LinearLayout datePlusGroup = new LinearLayout(this);
        LinearLayout dateListPlusLessonSelector = new LinearLayout(this);
        LinearLayout studentsPlusTableLayout = new LinearLayout(this);
        ScrollView studentsPlusTableScrollView = new ScrollView(this);

        groupSelector = new GroupSelector(this);
        dateSelector = new DateSelector(this);
        lessonSelector = new LessonSelector(this);

        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < 5; i++) dates.add(String.valueOf(i));

        ArrayList<String> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) students.add("Какойто студент С.С");
        StudentList studentList = new StudentList(this, students);

        dateList = new DateList(this, dates); // @TODO
        dateList.setOnTouchListener(this);

        tableWithMarks = new TableWithMarks(this, students.size(), dates.size()); // TODO
        tableWithMarks.setOnTouchListener(this);

        studentsPlusTableLayout.addView(studentList);
        studentsPlusTableLayout.addView(new VerticalLine(this, Config.getSeparateLineColor()));
        studentsPlusTableLayout.addView(tableWithMarks);

        studentsPlusTableScrollView.addView(studentsPlusTableLayout);

        dateListPlusLessonSelector.addView(lessonSelector);
        dateListPlusLessonSelector.addView(new VerticalLine(this, Config.getSeparateLineColor()));
        dateListPlusLessonSelector.addView(dateList);
        dateListPlusLessonSelector.setBackgroundColor(Color.LTGRAY);

        datePlusGroup.addView(groupSelector);
        datePlusGroup.addView(new VerticalLine(this, Config.getSeparateLineColor()));
        datePlusGroup.addView(dateSelector);

        mainLay = new LinearLayout(this);
        mainLay.setOrientation(LinearLayout.VERTICAL);
        mainLay.setBackgroundDrawable(Config.getBackground(this));
        mainLay.addView(datePlusGroup);
        mainLay.addView(new HorizontalLine(this, Config.getSeparateLineColor()));
        mainLay.addView(dateListPlusLessonSelector);
        mainLay.addView(new HorizontalLine(this, Config.getSeparateLineColor()));
        mainLay.addView(studentsPlusTableScrollView);
    }


}

