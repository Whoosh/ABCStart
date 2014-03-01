package ru.journal.fspoPrj.journal.data_get_managers.visits_light;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.util.HashMap;

public class LightVisits implements Serializable {

    private int[] teachersID;
    private LightExercisesInfo[] exercisesInfo;
    private HashMap<Integer, Visit[]> studentVisits;

    public LightVisits(String visitsResponse) {
        try {
            JSONObject element = new JSONObject(visitsResponse);
            makeTeachers(element);
            makeExercisesInfo(element);
            makeStudentsVisits(element);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public HashMap<Integer, Visit[]> getStudentVisits() {
        return studentVisits;
    }

    public LightExercisesInfo[] getExercisesInfo() {
        return exercisesInfo;
    }

    private void makeStudentsVisits(JSONObject element) throws JSONException {
        JSONObject visitExercises = element.getJSONObject(VisitsKey.VISITS_EXERCISES.getKey());
        JSONArray studentsID = visitExercises.names();
        int studCount = studentsID.length();
        studentVisits = new HashMap<>(studCount);
        for (int i = 0; i < studCount; i++) {
            String studID = studentsID.getString(i);
            makeStudentVisits(visitExercises.getJSONArray(studID), studID);
        }
    }

    private void makeStudentVisits(JSONArray visitExercises, String studentsID) throws JSONException {
        int visitCount = visitExercises.length();
        Visit[] visits = new Visit[visitCount];
        for (int i = 0; i < visitCount; i++) {
            visits[i] = new Visit(visitExercises.getJSONObject(i));
        }
        studentVisits.put(Integer.valueOf(studentsID), visits);
    }

    private void makeExercisesInfo(JSONObject element) throws JSONException {
        JSONArray exercisesInfo = element.getJSONArray(VisitsKey.GROUP_EXERCISES.getKey());
        int len = exercisesInfo.length();
        this.exercisesInfo = new LightExercisesInfo[len];
        for (int i = 0; i < len; i++) {
            this.exercisesInfo[i] = new LightExercisesInfo(exercisesInfo.getJSONObject(i));
        }
    }

    private void makeTeachers(JSONObject element) throws JSONException {
        JSONArray teachers = element.getJSONArray(VisitsKey.LESSON_TEACHERS.getKey());
        int len = teachers.length();
        teachersID = new int[len];
        for (int i = 0; i < len; i++) {
            teachersID[i] = teachers.getJSONObject(i).getInt(VisitsKey.TEACHER_ID.getKey());
        }
    }

    private static enum VisitsKey implements IKeyApi {

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
