package ru.journal.fspoPrj.journal.edit_journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.action_bar.TeacherJournalInfoBar;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.edit_journal.elements.cell_status_setters.CellLongClickDialog;
import ru.journal.fspoPrj.journal.edit_journal.elements.date_slider.TeacherDateSlider;
import ru.journal.fspoPrj.journal.edit_journal.elements.lable.LessonLabel;
import ru.journal.fspoPrj.journal.edit_journal.elements.main_table.TeacherTableWithMarks;
import ru.journal.fspoPrj.journal.edit_journal.elements.right_side_journal_menu.RightSideJournalMenu;
import ru.journal.fspoPrj.journal.looking_journal.elements.data_slider.DateElement;
import ru.journal.fspoPrj.journal.looking_journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.EvolutionCell;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class TeacherJournalActivity extends Activity implements
        View.OnTouchListener, View.OnClickListener,
        TeacherJournalInfoBar.OnGroupSelected,
        RightSideJournalMenu.JournalEditsCallBack,
        View.OnLongClickListener {

    public static final String EMPTY = "";

    private static EditJournalsCommunicator jC;

    private LinearLayout mainLay;
    private HorizontalLine groupSelectorSeparateLine;
    private StudentList studentList;

    private TeacherDateSlider dateSlider;
    private TeacherJournalInfoBar teacherJournalInfoBar;
    private TeacherTableWithMarks tableWithMarks;

    private LessonLabel lessonLabel;

    private RightSideJournalMenu functionalRightBar;
    private CellLongClickDialog cellLongClickDialog;

    public void initElements() {
        LinearLayout datePlusMatrix = new LinearLayout(this);
        LinearLayout groupSelectorPlusStudents = new LinearLayout(this);
        HorizontalScrollView datePlusCellMatrixScroller = new HorizontalScrollView(this);

        groupSelectorSeparateLine = new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth());
        teacherJournalInfoBar = new TeacherJournalInfoBar(this);
        lessonLabel = new LessonLabel(this, EMPTY);
        mainLay = new LinearLayout(this);
        dateSlider = new TeacherDateSlider(this);
        studentList = new StudentList(this);
        tableWithMarks = new TeacherTableWithMarks(this);
        functionalRightBar = new RightSideJournalMenu(this);
        cellLongClickDialog = new CellLongClickDialog();

        lessonLabel.setTextSize(Config.getEditJournalLessonLabelTextSize());
        mainLay.setBackgroundColor(Color.WHITE);
        groupSelectorSeparateLine.setVisibility(jC == null || jC.isEmpty() ? View.INVISIBLE : View.VISIBLE);

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth()));
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(lessonLabel);
        groupSelectorPlusStudents.addView(groupSelectorSeparateLine);
        groupSelectorPlusStudents.addView(studentList);

        mainLay.addView(groupSelectorPlusStudents);
        mainLay.addView(datePlusCellMatrixScroller);

        tableWithMarks.setRightFunctions(functionalRightBar);

        studentList.setOnTouchListener(this);
        lessonLabel.setOnClickListener(this);
        tableWithMarks.setOnTouchListener(this);
        dateSlider.setClickListener(this);
        functionalRightBar.setCallBack(this);
        tableWithMarks.setOnCellsShortClickListener(this);
        tableWithMarks.setOnCellsLongClickListener(this);
        dateSlider.setClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initElements();
        if (jC == null) {
            jC = new EditJournalsCommunicator(this);
        }
        startActionMode(teacherJournalInfoBar);
        setContentView(mainLay);
    }

    private void handlingResult(int resultCode) {
        switch (resultCode) {
            case EditJournalsCommunicator.TEACHER_LESSONS_QUERY: {
                teacherJournalInfoBar.setState(jC);
            }
            break;
            case EditJournalsCommunicator.FULL_VISITS_QUERY: {
                // TODO
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditJournalsCommunicator.RESULT_FAIL) {
            return;
        }
        if (data != null) {
            jC.cacheData(data, resultCode);
            handlingResult(resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        jC = null;
        super.onBackPressed();
    }

    @Override
    public void onGroupSelected(TeacherGroup group) {
        System.out.println(group);
        if (group.equals(jC.getSelectedGroup())) return;
        lessonLabel.setSelectedLesson(group);
        jC.setTeacherGroup(group);
        jC.sendVisitsQueryByGroup(group);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_UP && view.equals(studentList)) {
            tableWithMarks.scrollScrollerTo(0, studentList.getScrollY());
            return false;
        }
        tableWithMarks.scrollScrollerTo(0, studentList.getScrollY());
        studentList.scrollTo(0, tableWithMarks.getScrollerY());
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        lessonLabel.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lessonLabel.restoreState(savedInstanceState);
        teacherJournalInfoBar.setState(jC);
    }

    @Override
    public void journalMenuClicked(int keyCode) {
        switch (keyCode) {
            case RightSideJournalMenu.PLUS: {
                System.out.println("IM click plus");
                break;
            }
            case RightSideJournalMenu.EDIT: {
                System.out.println("Im click Edit");
                break;
            }
            case RightSideJournalMenu.REMOVE: {
                System.out.println("Im click Remove");
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof DateElement) {
            handleDateElementClick((DateElement) view);
        } else if (view instanceof EvolutionCell) {
            handleCellShortClick((EvolutionCell) view);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view instanceof EvolutionCell) {
            handleCellLongClick((EvolutionCell) view);
        }
        return true;
    }

    private void handleCellLongClick(EvolutionCell cell) {
        // TODO запрос
        cellLongClickDialog.setPreparingCell(cell);
        cellLongClickDialog.show(getFragmentManager(), EMPTY);
    }

    private void handleCellShortClick(EvolutionCell cell) {
        // TODO запрос и по ответу, уже мейкаем.
        makeShortClickResultOnCell(cell);
    }

    private void makeShortClickResultOnCell(EvolutionCell cell) {
        // TODO запрос
        cell.changeComingStatus();
    }

    private void handleDateElementClick(DateElement element) {
        setColorOnColumn(element, Color.GREEN);
        // TODO
    }

    private void setColorOnColumn(DateElement element, int color) {
        element.setBackgroundColor(color);
        tableWithMarks.setColumnColor(element.getTableIndex(), color);
    }


}
