package ru.journal.fspoPrj.journal.data_get_managers.visits_light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LightVisits implements Serializable {

    protected int[] teachersID;
    protected ArrayList<LightExercisesInfo> exercisesInfo;
    protected HashMap<Integer, ArrayList<Visit>> studentVisits;
    protected boolean reverse;

    public LightVisits(String visitsResponse, boolean fuckingReverse) {
        this.reverse = fuckingReverse;
        try {
            JSONObject element = new JSONObject(visitsResponse);
            makeTeachers(element);
            makeExercisesInfo(element);
            makeStudentsVisits(element);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public HashMap<Integer, ArrayList<Visit>> getStudentVisits() {
        return studentVisits;
    }

    public ArrayList<LightExercisesInfo> getExercisesInfo() {
        return exercisesInfo;
    }

    protected void makeStudentsVisits(JSONObject element) throws JSONException {
        JSONObject visitExercises = element.getJSONObject(VisitsKey.VISITS_EXERCISES.getKey());
        JSONArray studentsID = visitExercises.names();
        int studCount = studentsID.length();
        studentVisits = new HashMap<>(studCount);
        for (int i = 0; i < studCount; i++) {
            String studID = studentsID.getString(i);
            if (reverse)
                makeStudentVisitRevers(visitExercises.getJSONArray(studID), studID);
            else
                makeStudentVisitsNormal(visitExercises.getJSONArray(studID), studID);
        }
    }

    private void makeStudentVisitsNormal(JSONArray visitExercises, String studentID) {
        Integer studID = Integer.valueOf(studentID);
        ArrayList<Visit> visits = new ArrayList<>(exercisesInfo.size());
        for (int i = 0; i < exercisesInfo.size(); i++) {
            try {
                visits.add(new Visit(visitExercises.getJSONObject(i), studID));
            } catch (JSONException e) {
                visits.add(new Visit());
            }
        }
        studentVisits.put(studID, visits);
    }

    private void makeStudentVisitRevers(JSONArray visitExercises, String studentID) {
        Integer studID = Integer.valueOf(studentID);
        ArrayList<Visit> visits = new ArrayList<>(exercisesInfo.size());
        for (int i = 0; i < exercisesInfo.size(); i++) {
            try {
                visits.add(new Visit(visitExercises.getJSONObject(exercisesInfo.size() - i - 1), studID));
            } catch (JSONException e) {
                visits.add(new Visit());
            }
        }
        studentVisits.put(studID, visits);
    }

    private void makeExercisesInfo(JSONObject element) throws JSONException {
        JSONArray exercisesInfo = element.getJSONArray(VisitsKey.GROUP_EXERCISES.getKey());
        int len = exercisesInfo.length();
        this.exercisesInfo = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            this.exercisesInfo.add(new LightExercisesInfo(exercisesInfo.getJSONObject(i)));
        }
    }

    private void makeTeachers(JSONObject element) {
        try {
            JSONArray teachers = element.getJSONArray(VisitsKey.LESSON_TEACHERS.getKey());
            int len = teachers.length();
            teachersID = new int[len];
            for (int i = 0; i < len; i++) {
                teachersID[i] = teachers.getJSONObject(i).getInt(VisitsKey.TEACHER_ID.getKey());
            }
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public static enum VisitsKey implements IKeyApi {

        LESSON_TEACHERS("lessonTeachers"),
        GROUP_EXERCISES("groupExercises"),
        VISITS_EXERCISES("visitsExercises"),
        TEACHER_ID("id");
        private final String key;

        private VisitsKey(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public int getIntValue(JSONObject data) {
            return parser.parseInt(key, data);
        }

        @Override
        public String getStringValue(JSONObject data) {
            return parser.parseString(key, data);
        }
    }
}
