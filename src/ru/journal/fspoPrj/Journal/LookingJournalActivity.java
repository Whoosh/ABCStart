package ru.journal.fspoPrj.journal;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.journal.callbacks.Lessons;
import ru.journal.fspoPrj.journal.callbacks.Switcher;
import ru.journal.fspoPrj.public_code.configs.LookingJournalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.journal.head_selector.DateSelector;
import ru.journal.fspoPrj.journal.head_selector.GroupSelector;
import ru.journal.fspoPrj.journal.head_selector.LessonSelector;
import ru.journal.fspoPrj.journal.main_table.DateList;
import ru.journal.fspoPrj.journal.main_table.StudentList;
import ru.journal.fspoPrj.journal.main_table.TableWithMarks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LookingJournalActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private final String DATE_KEY = "IndexOfDate";
    private final String GROUP_KEY = "Group";

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
        // сохранение параметров выбора даты
        outState.putInt(DATE_KEY, dateSelector.getIndexOfSelectedDate());

        // сохранение параметров выбора группы
        outState.putByte(GROUP_KEY, groupSelector.getOldFocusedIndex());
    }

    private void loadStateOnRotateEvent(Bundle savedInstanceState) {
        // загрузка параметров выбора даты
        dateSelector.setIndexOfSelectedDate(savedInstanceState.getInt(DATE_KEY));
        dateSelector.refreshVisualState();

        // загрузка параметров выбора группы
        groupSelector.setOldFocusedIndex(savedInstanceState.getByte(GROUP_KEY));
        groupSelector.refreshVisualState();
    }


    public static void setAllStaticFieldsIsNull() {
        lessonSelector = null;
        listOfLessonsNames = null;
    }

    private void initElements() {
        mainLay = new LinearLayout(this);
        LinearLayout datePlusGroup = new LinearLayout(this);
        LinearLayout dateListPlusLessonSelector = new LinearLayout(this);
        LinearLayout studentsPlusTableLayout = new LinearLayout(this);
        ScrollView studentsPlusTable = new ScrollView(this);

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
        studentsPlusTableLayout.addView(new VerticalLine(this, LookingJournalConfig.getSeparateLineColor()));
        studentsPlusTableLayout.addView(tableWithMarks);

        studentsPlusTable.addView(studentsPlusTableLayout);

        dateListPlusLessonSelector.addView(lessonSelector);
        dateListPlusLessonSelector.addView(new VerticalLine(this, LookingJournalConfig.getSeparateLineColor()));
        dateListPlusLessonSelector.addView(dateList);

        datePlusGroup.addView(groupSelector);
        datePlusGroup.addView(new VerticalLine(this, LookingJournalConfig.getSeparateLineColor()));
        datePlusGroup.addView(dateSelector);

        mainLay.setOrientation(LinearLayout.VERTICAL);
        mainLay.setBackgroundDrawable(LookingJournalConfig.getBackground(this));
        mainLay.addView(datePlusGroup);
        mainLay.addView(new HorizontalLine(this, LookingJournalConfig.getSeparateLineColor()));
        mainLay.addView(dateListPlusLessonSelector);
        mainLay.addView(new HorizontalLine(this, LookingJournalConfig.getSeparateLineColor()));
        mainLay.addView(studentsPlusTable);
    }


}

