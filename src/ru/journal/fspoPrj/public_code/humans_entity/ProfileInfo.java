package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLesson;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class ProfileInfo extends Human {

    public static enum Status {TEACHER, STUDENT, TEACHER_AKA_STUDENT}

    private final String phone;
    private final String photoLink;
    private final String mail;

    private int group;
    private Status status;
    private TeacherLessons teacherLessons;

    public ProfileInfo(JSONObject element) {
        super(element);
        this.photoLink = ProfileKeys.PHOTO.getStringValue(element);
        this.mail = ProfileKeys.EMAIL.getStringValue(element);
        this.phone = ProfileKeys.PHONE.getStringValue(element);
    }

    public ProfileInfo(JSONObject element, int group) {
        this(element);
        this.group = group;
        this.status = Status.STUDENT;
    }

    public ProfileInfo(JSONObject element, Status status) {
        this(element);
        this.status = status;
    }

    public void setLessons(String value) {
        teacherLessons = new TeacherLessons(value);
    }

    public TeacherLesson[] getLessons() {
        return teacherLessons.getLessons();
    }

    public boolean hasLessons() {
        return teacherLessons != null;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStringGroup() {
        return String.valueOf(group);
    }

    public boolean isTeacher() {
        return status == Status.TEACHER;
    }

    public boolean isStudent() {
        return status == Status.STUDENT;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private static enum ProfileKeys implements IKeyApi {
        PHOTO("photo"),
        EMAIL("email"),
        PHONE("phone");

        private final String key;

        private ProfileKeys(String key) {
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
