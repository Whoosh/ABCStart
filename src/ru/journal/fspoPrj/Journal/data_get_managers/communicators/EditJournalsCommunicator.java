package ru.journal.fspoPrj.journal.data_get_managers.communicators;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.journal.EvolutionCell;
import ru.journal.fspoPrj.journal.action_crud.CreateExerciseExecutor;
import ru.journal.fspoPrj.journal.action_crud.DeleteExerciseExecutor;
import ru.journal.fspoPrj.journal.action_crud.UpdateTypeExerciseExecutor;
import ru.journal.fspoPrj.journal.cell_change_params_executors.*;
import ru.journal.fspoPrj.journal.data_get_managers.edit_visit_full.TeacherVisits;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroupsExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.journal.data_get_managers.edit_visit_full.TeacherVisitsExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.public_code.humans_entity.Student;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.util.ArrayList;

public class EditJournalsCommunicator extends ServerCommunicator {

    public static final int TEACHER_LESSONS_QUERY = 1;
    public static final int TEACHER_JOURNAL_VISITS_QUERY = 2;
    public static final int STUDENT_COMING_STATUS_PARAM = 3;
    public static final int STUDENT_RANK_CHANGE = 4;
    public static final int STUDENT_POWER_UP_STATE_QUERY = 5;
    public static final int STUDENT_NEEDED_ON_PAIR_QUERY = 6;
    public static final int STUDENT_SLOWER_STATUS_QUERY = 7;
    public static final int STUDENT_POINT_IMPORTANT_STATUS_QUERY = 8;
    public static final int STUDENT_EXCLUSIVE_STATUS_POINT_QUERY = 9;
    public static final int STUDENT_REMOVE_POINT_STATUS_QUERY = 10;
    public static final int TEACHER_CREATE_NEW_EXERCISE_QUERY = 11;
    public static final int TEACHER_DELETE_EXERCISE_QUERY = 12;
    public static final int TEACHER_EDIT_TYPE_EXERCISE_QUERY = 13;

    public static final String EMPTY_STUDENT_NAME = "";
    public static final String EMPTY = "";
    public static final String REMOVE_POINT_VALUE = "none";
    public static final String DEFAULT_EXERCISE_TYPE = "1";
    public static final String EMPTY_EXERCISE_TOPIC = "";
    public static final String TRUE_STRING_LOL = "true";

    private int lastQueryID;
    private Activity parentCaller;

    private String teacherLessonsQuery;
    private String teacherJournalQuery;
    private String studentComingParamChangeQuery;
    private String studentRankChangeQuery;
    private String studentPowerStateQuery;
    private String studentNeededOnPairQuery;
    private String studentSlowerStatusQuery;
    private String studentImportantPointStatusQuery;
    private String studentExclusivePointStatusQuery;
    private String studentRemovePointStatusQuery;
    private String teacherCreateNewExerciseQuery;
    private String teacherDeleteExerciseQuery;
    private String teacherEditExerciseTypeQuery;

    private TeacherLessons teacherJournal;
    private TeacherVisits teacherVisits;
    private TeacherGroup teacherGroup;

    private String lastSelectedLessonID;
    private String lastSelectedGroupID;
    private String lastSelectedForDeleteExercisesID;

    public EditJournalsCommunicator(Activity parentCaller) {
        this.parentCaller = parentCaller;
        sendTeacherLessonsQuery();
    }

    private void sendTeacherLessonsQuery() {
        teacherLessonsQuery = APIQuery.GET_TEACHER_LESSON.getLink(getToken(), getYearID(), getMyID());
        lastQueryID = TEACHER_LESSONS_QUERY;
        sendQueryToServer(parentCaller, makeExecutor());
    }

    public void resendLastQuery() {
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    @Override
    protected MainExecutor makeExecutor() {
        switch (lastQueryID) {
            case TEACHER_LESSONS_QUERY: {
                return new TeacherGroupsExecutor(teacherLessonsQuery, lastQueryID);
            }
            case TEACHER_JOURNAL_VISITS_QUERY: {
                return new TeacherVisitsExecutor(teacherJournalQuery, lastQueryID);
            }
            case STUDENT_COMING_STATUS_PARAM: {
                return new StudentComingStatusExecutor(studentComingParamChangeQuery, lastQueryID);
            }
            case STUDENT_RANK_CHANGE: {
                return new StudentRankChangeQuery(studentRankChangeQuery, lastQueryID);
            }
            case STUDENT_POWER_UP_STATE_QUERY: {
                return new StudentPowerChangeExecutor(studentPowerStateQuery, lastQueryID);
            }
            case STUDENT_NEEDED_ON_PAIR_QUERY: {
                return new StudentNeededStatusChangeExecutor(studentNeededOnPairQuery, lastQueryID);
            }
            case STUDENT_POINT_IMPORTANT_STATUS_QUERY: {
                return new StudentPointImportantStatusChangeExecutor(studentImportantPointStatusQuery, lastQueryID);
            }
            case STUDENT_SLOWER_STATUS_QUERY: {
                return new StudentSlowerStatusChangeExecutor(studentSlowerStatusQuery, lastQueryID);
            }
            case STUDENT_EXCLUSIVE_STATUS_POINT_QUERY: {
                return new StudentExclusivePointStateExecutor(studentExclusivePointStatusQuery, lastQueryID);
            }
            case STUDENT_REMOVE_POINT_STATUS_QUERY: {
                return new StudentRemoveStatusPointExecutor(studentRemovePointStatusQuery, lastQueryID);
            }
            case TEACHER_CREATE_NEW_EXERCISE_QUERY: {
                return new CreateExerciseExecutor(teacherCreateNewExerciseQuery, lastQueryID);
            }
            case TEACHER_DELETE_EXERCISE_QUERY: {
                return new DeleteExerciseExecutor(teacherDeleteExerciseQuery, lastQueryID);
            }
            case TEACHER_EDIT_TYPE_EXERCISE_QUERY: {
                return new UpdateTypeExerciseExecutor(teacherEditExerciseTypeQuery, lastQueryID);
            }
        }
        return null;
    }

    public void cacheData(Intent data, int resultCode) {
        switch (resultCode) {
            case TEACHER_JOURNAL_VISITS_QUERY: {
                teacherVisits = (TeacherVisits) data.getSerializableExtra(teacherJournalQuery);
                data.removeExtra(teacherJournalQuery);
            }
            break;
            case TEACHER_LESSONS_QUERY: {
                teacherJournal = (TeacherLessons) data.getSerializableExtra(teacherLessonsQuery);
                data.removeExtra(teacherLessonsQuery);
            }
            break;
            case TEACHER_CREATE_NEW_EXERCISE_QUERY: {
                teacherVisits.addNewExercise((LightExercisesInfo) data.getSerializableExtra(teacherCreateNewExerciseQuery));
                data.removeExtra(teacherCreateNewExerciseQuery);
            }
            break;
        }
    }

    public TeacherLessons getTeacherJournal() {
        return this.teacherJournal;
    }

    public void setTeacherGroup(TeacherGroup teacherGroup) {
        this.teacherGroup = teacherGroup;
    }

    public TeacherGroup getSelectedGroup() {
        return teacherGroup;
    }

    public void sendVisitsQueryByGroup(TeacherGroup group) {
        this.teacherGroup = group;
        lastQueryID = TEACHER_JOURNAL_VISITS_QUERY;
        lastSelectedGroupID = teacherGroup.getStringGroupID();
        lastSelectedLessonID = teacherGroup.getStringLessonID();
        teacherJournalQuery = APIQuery.GET_TEACHER_JOURNAL_VISITS.getLink(getToken(), lastSelectedLessonID, lastSelectedGroupID, getYearID());
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public ArrayList<Student> getStudents() {
        return teacherVisits.getStudents();
    }

    public ArrayList<LightExercisesInfo> getLightExercisesInfo() {
        return teacherVisits.getExercisesInfo();
    }

    public LightVisits getLightVisits() {
        return teacherVisits;
    }

    public void changeStudentRankQuery(EvolutionCell cell, String value) {
        lastQueryID = STUDENT_RANK_CHANGE;
        studentRankChangeQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.RANK.getName(), value);
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void makeStudentComingCellStatusChangeQuery(EvolutionCell cell) {
        lastQueryID = STUDENT_COMING_STATUS_PARAM;
        studentComingParamChangeQuery = APIQuery.GET_CELL_STATUS_CHANGE
                .getLink(getToken(), cell.getStringVisitID(), CellFieldsNames.COMING_ON_PAIR.getName(), mathStatus(cell));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void changeStudentPowerQuery(EvolutionCell cell, int state) {
        lastQueryID = STUDENT_POWER_UP_STATE_QUERY;
        studentPowerStateQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.POWER_STATE.getName(), String.valueOf(state));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void changeStudentNeededOnPairQuery(EvolutionCell cell, int state) {
        lastQueryID = STUDENT_NEEDED_ON_PAIR_QUERY;
        studentNeededOnPairQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.NEEDED_STUDENT_ON_PAIR.getName(), String.valueOf(state));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void changeStudentSlowerStatusQuery(EvolutionCell cell, int state) {
        lastQueryID = STUDENT_SLOWER_STATUS_QUERY;
        studentSlowerStatusQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.STUDENT_SLOWER.getName(), String.valueOf(state));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void changeStudentPointImportantStatusQuery(EvolutionCell cell, int state) {
        lastQueryID = STUDENT_POINT_IMPORTANT_STATUS_QUERY;
        studentImportantPointStatusQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.STUDENT_IMPORTANT_POINT_WEIGHT.getName(), String.valueOf(state));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void changeStudentExclusivePointStatus(EvolutionCell cell, int state) {
        lastQueryID = STUDENT_EXCLUSIVE_STATUS_POINT_QUERY;
        studentExclusivePointStatusQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.STUDENT_IMPORTANT_POINT.getName(), String.valueOf(state));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void removePointStatusQuery(EvolutionCell cell) {
        lastQueryID = STUDENT_REMOVE_POINT_STATUS_QUERY;
        studentRemovePointStatusQuery = APIQuery.GET_CELL_STATUS_CHANGE.getLink(getToken(), cell.getStringVisitID(),
                CellFieldsNames.RANK.getName(), REMOVE_POINT_VALUE);
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    private String mathStatus(EvolutionCell cell) {
        return cell.getComingCellStatus() == 1 ? "0" : "1";
    }

    public boolean canBeGetFromCache() {
        return !(teacherVisits == null || teacherJournal == null);
    }

    public boolean isTeacherVisitsEmpty() {
        return (teacherVisits == null);
    }

    public boolean isTeacherLessonsEmpty() {
        return (teacherJournal == null);
    }

    public String getStudentInfo(int studentID) {
        for (Student student : teacherVisits.getStudents()) {
            if (student.getIntegerID() == studentID) {
                return student.getShortName();
            }
        }
        return EMPTY_STUDENT_NAME;
    }

    public String getDMDate(int exerciseID) {
        for (LightExercisesInfo lightExercisesInfo : teacherVisits.getExercisesInfo()) {
            if (lightExercisesInfo.getExercisesID() == exerciseID) {
                return lightExercisesInfo.getDMDate();
            }
        }
        return EMPTY;
    }

    public void makeCreateExerciseQuery(int lastPairSelected) {
        lastQueryID = TEACHER_CREATE_NEW_EXERCISE_QUERY;
        teacherCreateNewExerciseQuery = APIQuery.GET_CREATE_NEW_EXERCISE.getLink(getToken(), getYearID(),
                lastSelectedLessonID, DEFAULT_EXERCISE_TYPE, lastSelectedGroupID, String.valueOf(lastPairSelected), EMPTY_EXERCISE_TOPIC,
                TRUE_STRING_LOL);
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void makeDeleteExercise(String selectedExerciseID) {
        lastQueryID = TEACHER_DELETE_EXERCISE_QUERY;
        lastSelectedForDeleteExercisesID = selectedExerciseID;
        teacherDeleteExerciseQuery = APIQuery.GET_DELETE_EXERCISE.getLink(getToken(), selectedExerciseID);
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void makeRemoveLastSelectedForDeleteExercises() {
        teacherVisits.removeExercise(lastSelectedForDeleteExercisesID);
    }

    public void makeEditTypeQuery(int type, String selectedExerciseID) {
        lastQueryID = TEACHER_EDIT_TYPE_EXERCISE_QUERY;
        teacherEditExerciseTypeQuery = APIQuery.GET_UPDATE_EXERCISE.getLink(getToken(),
                selectedExerciseID, EMPTY_EXERCISE_TOPIC, String.valueOf(type));
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public void changeExerciseType(int type, int exerciseID) {
        for (LightExercisesInfo exercisesInfo : getLightVisits().getExercisesInfo()) {
            if (exercisesInfo.getExercisesID() == exerciseID) {
                exercisesInfo.changeType(type);
                break;
            }
        }
    }

    private static enum CellFieldsNames {
        COMING_ON_PAIR("presence"),
        RANK("point"),
        POWER_STATE("performance"),
        NEEDED_STUDENT_ON_PAIR("need"),
        STUDENT_SLOWER("delay"),
        STUDENT_IMPORTANT_POINT("mark_need"),
        STUDENT_IMPORTANT_POINT_WEIGHT("weight");

        private final String name;

        CellFieldsNames(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
