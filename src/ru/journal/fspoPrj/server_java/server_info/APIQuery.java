package ru.journal.fspoPrj.server_java.server_info;

import ru.journal.fspoPrj.public_code.Logger;

public enum APIQuery {
    // TODO This all in file or make this place more beautiful
    AUTHORIZATION("GET /api/authentication/?login=", "&pass="),
    LOG_OUT("GET /api/logout/?ssid="),
    GET_PROFILE("GET /api/getProfile/?ssid=", "&user_id="),
    GET_MIGHT("GET /api/getRoles/?ssid=", "&user_id="),
    GET_GROUP_LIST("GET /api/getGroupsList/?ssid=", "&year_id="),
    GET_TEACHER_LESSON("GET /api/getTeacherLessons/?ssid=", "&year_id=", "&user_id="),
    GET_GROUP_JOURNAL("GET /api/getGroupsJournals/?ssid=", "&year_id="),
    GET_JOURNAL_VISITS_BY_GROUP_FULL
            ("GET /api/getJournalVisitsByOffGroup/?ssid=", "&year_id=", "&lesson_id=", "&group_id="),
    GET_JOURNAL_VISITS_BY_GROUP_LIGHT
            ("GET /api/getJournalVisitsByOffGroupLight/?ssid=", "&year_id=", "&lesson_id=", "&group_id="),
    GET_TEACHER_JOURNAL_VISITS_BY_GROUP_LIGHT
            ("GET /api/getTeacherJournalVisitsByGroupLight/?ssid=", "&year_id=", "&teacherlesson_id=", "&group_id="),
    GET_USERS_LIST("GET /api/getuserslist/?ssid=", "&year_id="),
    GET_EXERCISES("GET /api/getTeacherJournalExercises/?ssid=", "&year_id=", "&group_id=", "&teacherlesson_id="),
    GET_GROUP_STUDENTS("GET /api/getGroupStudents/?ssid=", "&group_id=", "&year_id="),
    GET_TEACHER_JOURNAL_VISITS("GET /api/getTeacherJournalVisitsByGroupLight?ssid=", "&teacherlesson_id=", "&group_id=", "&year_id="),
    GET_CELL_STATUS_CHANGE("GET /api/updateVisit?ssid=", "&visit_id=", "&field=", "&value="),
    GET_CREATE_NEW_EXERCISE("GET /api/createNewExercise?ssid=", "&year_id=", "&teacherlesson_id=", "&type=", "&group_id=", "&period="
            , "&topic=", "&get_visits="),
    GET_UPDATE_EXERCISE("GET /api/updateExerciseInfo?ssid=", "&exercise_id=", "&topic=", "&type="),
    GET_DELETE_EXERCISE("GET /api/deleteExercise?ssid=", "&exercise_id="),
    GET_MY_MESSAGE("GET /api/getMyMessages/?ssid=", "&year_id="),
    GET_CHAT_MESSAGE("GET /api/getChatMessages?ssid=","&chat_user_id="),
    GET_SENT_MESSAGE("GET /api/sendMessage?ssid=","&receiver_id=","&text="),

    EMPTY_QUERY("GET /api");

    private final String[] keys;

    private APIQuery(String... keys) {
        this.keys = keys;
    }

    public String getLink(String... values) {
        if (values.length == 0) return keys[0];
        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < values.length; i++) {
                builder.append(keys[i]);
                builder.append(values[i]);
            }
            return builder.toString();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.printError(ex, getClass());
            return EMPTY_QUERY.toString();
        }
    }

}
