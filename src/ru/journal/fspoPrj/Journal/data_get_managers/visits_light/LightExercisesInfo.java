package ru.journal.fspoPrj.journal.data_get_managers.visits_light;

import android.graphics.Color;
import android.nfc.FormatException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;

public class LightExercisesInfo implements Serializable {

    public static final String DATE_SEPARATOR = "-";
    private final int exercisesId;
    private final String dMDate;

    private final int type;

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

    public LightExercisesInfo(JSONObject element) {
        exercisesId = ExercisesKeys.EXERCISES_ID.getIntValue(element);
        type = ExercisesKeys.TYPE.getIntValue(element);
        dMDate = ExercisesKeys.DATE_DM.getStringValue(element);
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

    private static enum ExercisesKeys implements IKeyApi {

        EXERCISES_ID("id"),
        TYPE("type"),
        DATE_DM("user_date"),
        LORD("lord"),
        OWNER("owner"),
        INCORRECT("incorrect"),
        EX_TIME("ex_time");

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
