package ru.journal.fspoPrj.journal.data_get_managers.edit_visit_full;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeacherVisits extends LightVisits implements Serializable {

    private ArrayList<Student> groupStudents;

    public TeacherVisits(String teacherJournal) {
        super(teacherJournal, true);
        groupStudents = new ArrayList<>();
        addStudents(teacherJournal);
    }

    private void addStudents(String teacherJournal) {
        try {
            JSONArray students = new JSONObject(teacherJournal).getJSONArray(TeacherVisitsKeys.GROUP_STUDENTS_KEY.getKey());
            for (int i = 0; i < students.length(); i++) {
                groupStudents.add(new Student(students.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public ArrayList<Student> getStudents() {
        return groupStudents;
    }

    public void addNewExercise(LightExercisesInfo exercisesInfo) {
        super.exercisesInfo.add(exercisesInfo);
        HashMap<Integer, ArrayList<Visit>> bufferedSet = new HashMap<>();
        ArrayList<Visit> updatedVisits;
        for (Map.Entry<Integer, ArrayList<Visit>> entry : studentVisits.entrySet()) {
            updatedVisits = new ArrayList<>();
            for (int i = 0; i < entry.getValue().size(); i++) {
                updatedVisits.add(entry.getValue().get(i));
            }
            try {
                updatedVisits.add(new Visit(
                        exercisesInfo.getExercisesID(),
                        entry.getKey(),
                        exercisesInfo.getServersNewVisitsID().getInt(String.valueOf(entry.getKey()))));
            } catch (JSONException e) {
                Logger.printError(e, getClass());
                updatedVisits.add(new Visit());
            }
            bufferedSet.put(entry.getKey(), updatedVisits);
        }
        super.studentVisits.clear();
        super.studentVisits.putAll(bufferedSet);
    }

    public void removeExercise(String lastSelectedForDeleteExercisesID) {
        int lsID = Integer.valueOf(lastSelectedForDeleteExercisesID);
        int visitIndex = 0;
        for (Iterator<LightExercisesInfo> iterator = super.exercisesInfo.iterator(); iterator.hasNext(); ) {
            LightExercisesInfo lightExercisesInfo = iterator.next();
            if (lightExercisesInfo.getExercisesID() == lsID) {
                iterator.remove();
                break;
            }
            visitIndex++;
        }
        for (Map.Entry<Integer, ArrayList<Visit>> entry : studentVisits.entrySet()) {
            entry.getValue().remove(visitIndex);
        }
    }

    private static enum TeacherVisitsKeys implements IKeyApi {
        GROUP_STUDENTS_KEY("groupStudents");

        private final String key;

        private TeacherVisitsKeys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public int getIntValue(JSONObject element) {
            return parser.parseInt(key, element);
        }

        @Override
        public String getStringValue(JSONObject element) {
            return parser.parseString(key, element);
        }
    }
}

