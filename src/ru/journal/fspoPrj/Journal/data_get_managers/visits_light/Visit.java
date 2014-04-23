package ru.journal.fspoPrj.journal.data_get_managers.visits_light;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;

public class Visit implements Serializable {

    public static final int EMPTY_VISIT = 1; // student cant be coming on pair

    private int visitNeed;

    public static enum VisitNeedState {NOT_SET, LOW, MIDDLE, HIGH}

    private int point;

    public static enum PointState {FAIL, TRUE, TWO, THREE, FOUR, FIVE}

    private int presence;

    public static enum PresentsState {DRINK_VODKA_WITH_BEAR, COMING_ON_LESSON}

    private int delay;

    public static enum DelayState {NOT_SET, SLOW, FAST, BATMAN}

    private int dropout;

    public static enum DropoutState {NOT_SET, POP_FROM_LESSON}

    private int performance;

    public static enum PerformanceState {NOT_SET, HIGH, LOW}

    private int weight;

    public static enum WeightState {NOT_SET, LIGHT, MIDDLE, OVER9000KG}

    private int markNeed;

    public static enum MarkNeedState {NOT_SET, MARKED}

    private int visitID;
    private int exercisesID;
    private boolean emptyCell;
    private int studentID;

    public Visit() {
        emptyCell = true;
    }

    public Visit(int exercisesID, int studentID,int visitID) {
        this.exercisesID = exercisesID;
        this.studentID = studentID;
        this.presence = PresentsState.COMING_ON_LESSON.ordinal();
        this.visitID = visitID;
    }

    public Visit(JSONObject element, int studentID) {
        exercisesID = VisitKey.EXERCISE_ID.getIntValue(element);
        this.studentID = studentID;
        if (element.names().length() == EMPTY_VISIT) {
            emptyCell = true;
            return;
        }

        visitID = VisitKey.VISIT_ID.getIntValue(element);
        visitNeed = VisitKey.VISIT_NEED.getIntValue(element);
        point = VisitKey.POINT.getIntValue(element);
        presence = VisitKey.PRESENCE.getIntValue(element);
        delay = VisitKey.DELAY.getIntValue(element);
        dropout = VisitKey.DROPOUT.getIntValue(element);
        performance = VisitKey.PERFORMANCE.getIntValue(element);
        weight = VisitKey.WEIGHT.getIntValue(element);
        markNeed = VisitKey.MARK_NEED.getIntValue(element);
    }

    public String getStringStudentID() {
        return String.valueOf(studentID);
    }

    public int getStudentID() {
        return studentID;
    }

    public int getVisitID() {
        return visitID;
    }

    public int getExercisesID() {
        return exercisesID;
    }

    public int getVisitNeed() {
        return visitNeed;
    }

    public int getPoint() {
        return point;
    }

    public int getPresence() {
        return presence;
    }

    public int getDelay() {
        return delay;
    }

    public int getDropout() {
        return dropout;
    }

    public int getPerformance() {
        return performance;
    }

    public int getWeight() {
        return weight;
    }

    public int getMarkNeed() {
        return markNeed;
    }

    public int[] getAllStates() {
        return new int[]{
                getDelay(),
                getDropout(),
                getMarkNeed(),
                getPerformance(),
                getVisitNeed(),
                getPoint(),
                getDropout(),
                getPresence(),
                getWeight(),
                getEmptyCell()
        };
    }

    public int getEmptyCell() {
        return isEmptyCell() ? 1 : 0;
    }

    public boolean isEmptyCell() {
        return emptyCell;
    }

    public void setPresence(PresentsState state) {
        this.presence = state.ordinal();
    }

    public void setVisitNeed(VisitNeedState state) {
        this.visitNeed = state.ordinal();
    }

    public void setPoint(PointState state) {
        this.point = state.ordinal();
    }

    public void setDelay(DelayState state) {
        this.delay = state.ordinal();
    }

    public void setDropout(DropoutState state) {
        this.dropout = state.ordinal();
    }

    public void setPerformance(PerformanceState state) {
        this.performance = state.ordinal();
    }

    public void setWeight(WeightState state) {
        this.weight = state.ordinal();
    }

    public void setMarkNeed(MarkNeedState state) {
        this.markNeed = state.ordinal();
    }

    private static enum VisitKey implements IKeyApi {

        VISIT_ID("visit_id"),
        VISIT_NEED("visit_need"),
        POINT("point"),
        PRESENCE("presence"),
        DELAY("delay"),
        DROPOUT("dropout"),
        PERFORMANCE("performance"),
        WEIGHT("weight"),
        MARK_NEED("mark_need"),
        EXERCISE_ID("exercise_id");

        private final String key;

        private VisitKey(String key) {
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

