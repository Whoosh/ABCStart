package ru.journal.fspoPrj.journal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.LookingJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.elements.data_slider.DateSlider;
import ru.journal.fspoPrj.journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class LookingJournalActivity extends JournalActivity {

    protected LinearLayout datePlusMatrix;

    @Override
    public void initElements() {
        super.initElements();

        datePlusMatrix = new LinearLayout(this);

        LinearLayout groupSelectorPlusStudents = new LinearLayout(this);
        HorizontalScrollView datePlusCellMatrixScroller = new HorizontalScrollView(this);

        dateSlider = new DateSlider(this);
        studentList = new StudentList(this);
        tableWithMarks = new TableWithMarks(this);

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth()));
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(groupButton);
        groupSelectorPlusStudents.addView(groupSelectorSeparateLine);
        groupSelectorPlusStudents.addView(studentList);

        mainLay.addView(groupSelectorPlusStudents);
        mainLay.addView(datePlusCellMatrixScroller);

        lessonsSelector.setSemesterButton(semesterButton);
        semesterButton.setOnClickListener(this);
        studentList.setOnTouchListener(this);
        groupButton.setOnClickListener(this);
        tableWithMarks.setOnTouchListener(this);
        semesterDialog.setCallBack(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initElements();
        super.setJournalCommunicator(LookingJournalsCommunicator.class, this);

        startActionMode(lessonsSelector);
        setContentView(mainLay);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(groupButton)) {
            handleGroupClick();
        } else if (view.equals(semesterButton)) {
            handleSemesterClick();
        }
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
    public void lessonSelected(GroupLesson lesson) {
        if (!selectedLesson.equals(lesson)) {
            sendVisitsQueryByLessonSelect(lesson);
            lessonsSelector.setLessonTitle(selectedLesson);
        }
    }

    @Override
    public void groupSelected(int groupNumber) {
        Group group = jC.getGroup(groupNumber);
        if (!selectedGroup.equals(group)) {
            selectedGroup = group;
            semesterDialog.setAllPossiblySemesters(jC.getAllSemesters(group));
            groupButton.setSelectedGroup(group);
            if (jC.getLessons(group, selectedSemester).length == 0) {
                selectedSemester = jC.getFirstPossiblySemester(selectedGroup);
                semesterButton.setSelectedSemester(selectedSemester);
            }
            sendVisitsQueryByGroupSelect(jC.getLessons(group, selectedSemester));
        }
    }

    @Override
    public void semesterSelected(int semester) {
        if (semester != selectedSemester) {
            selectedSemester = semester;
            semesterButton.setSelectedSemester(semester);
            lessonsSelector.setLessons(jC.getLessons(selectedGroup, selectedSemester));
            sendVisitsQueryByGroupSelect(jC.getLessons(selectedGroup, semester));
        }
    }

    @Override
    public void semesterDialogClosed() {
        semesterButton.setEnabled(true);
    }

    @Override
    public void semesterDialogOpened() {
        semesterButton.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == LookingJournalsCommunicator.RESULT_FAIL) {
            groupButton.refreshStateON();
            return;
        }
        if (data != null) {
            groupButton.disableRefreshState();
            jC.cacheData(data, resultCode);
            handlingResult(resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        groupDialog.saveState(outState);
        groupButton.saveState(outState);
        semesterButton.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreLessonStudents();
        restoreTable(jC.getLightVisits());
        groupDialog.restoreState(savedInstanceState);
        groupButton.restoreState(savedInstanceState);
        semesterButton.restoreState(savedInstanceState);
    }

}

