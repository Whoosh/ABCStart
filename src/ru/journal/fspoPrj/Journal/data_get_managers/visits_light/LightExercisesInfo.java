package ru.journal.fspoPrj.journal.data_get_managers.visits_light;

import android.graphics.Color;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class LightExercisesInfo implements Serializable {

    public static final String DATE_SEPARATOR = "-";
    public static final int DM_DATE_FORMAT_LENGTH = 5;
    public static final int YEAR_AND_SEPARATE_INDEX = 5;

    private String dMDate;
    private int exercisesId;
    private int type;

    private String serversNewVisitsID;

    public LightExercisesInfo(JSONObject element) {
        exercisesId = ExercisesKeys.EXERCISES_ID.getIntValue(element);
        type = ExercisesKeys.TYPE.getIntValue(element);
        dMDate = ExercisesKeys.DATE_DM.getStringValue(element);
        splitDate();
    }

    public LightExercisesInfo(JSONObject element, int type) {
        exercisesId = ExercisesKeys.SUBLIM_EXER_ID.getIntValue(element);
        dMDate = ExercisesKeys.EX_TIME.getStringValue(element);
        this.type = type;
        try {
            serversNewVisitsID = element.getJSONObject(ExercisesKeys.VISITS.getKey()).toString();
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public JSONObject getServersNewVisitsID() {
        try {
            return new JSONObject(serversNewVisitsID);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return null;
        }
    }

    private void splitDate() {
        if (dMDate.length() > DM_DATE_FORMAT_LENGTH) {
            dMDate = dMDate.substring(YEAR_AND_SEPARATE_INDEX);
        }
    }

    public int getExercisesID() {
        return exercisesId;
    }

    public int getType() {
        return type;
    }

    public String getDMDate() {
        return dMDate;
    }

    public String getStringExID() {
        return String.valueOf(getExercisesID());
    }

    public String getDateDay() {
        try {
            return dMDate.substring(dMDate.indexOf(DATE_SEPARATOR) + DATE_SEPARATOR.length());
        } catch (Exception ex) {
            Logger.printError(ex, getClass());
            return getDMDate();
        }
    }

    public void changeType(int type) {
        this.type = type;
    }

    public int getExerciseColor() {
        for (TypeState state : TypeState.values()) {
            if(state.ordinal() == type){
                return state.color;
            }
        }
        return 0;
    }

    public static enum TypeState {
        NOT_SET(Color.WHITE),
        NORMAL_LESSON(Color.WHITE),
        LAB_WORK(Color.parseColor("#A3A3A3")),
        ATTESTATION(Color.parseColor("#F8DDED")),
        TEST(Color.parseColor("#B57EDC")),
        EXAM(Color.parseColor("#B57EDC")),
        PRACTICE(Color.WHITE),
        CONSULTATION(Color.WHITE),
        CLASS_WORK(Color.parseColor("#A4FFFE"));

        private final int color;

        private TypeState(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }

        public int getEnID() {
            return ordinal();
        }
    }

    private static enum ExercisesKeys implements IKeyApi {

        EXERCISES_ID("id"),
        TYPE("type"),
        DATE_DM("user_date"),
        LORD("lord"),
        OWNER("owner"),
        INCORRECT("incorrect"),
        EX_TIME("ex_time"),
        SUBLIM_EXER_ID("exercise_id"),
        VISITS("visits");

        private final String key;

        private ExercisesKeys(String key) {
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
