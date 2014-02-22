package ru.journal.fspoPrj.journal.data_get_managers.groups_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Comparable<Group>, Serializable {

    private static final String ENTER = "\n";

    private static final int LOW = -1;
    private static final int UPPER = 1;
    private static final int EQUALS = 0;

    private final int groupNumber;
    private Student[] students;
    private GroupLesson[] groupLessons;
    private GroupLesson[] fistSemesterLessons;
    private GroupLesson[] lastSemesterLessons;

    public Group(int groupNumber, JSONArray studentsList) {
        this.groupNumber = groupNumber;
        makeStudents(studentsList);
    }

    private void makeStudents(JSONArray studentsList) {
        students = new Student[studentsList.length()];
        for (int i = 0; i < studentsList.length(); i++) {
            try {
                students[i] = new Student(studentsList.getJSONObject(i));
            } catch (JSONException e) {
                Logger.printError(e, getClass());
            }
        }
    }

    public void setGroupLessons(JSONArray groupLessonsResponse) throws JSONException {
        this.groupLessons = new GroupLesson[groupLessonsResponse.length()];
        for (int i = 0; i < groupLessons.length; i++) {
            groupLessons[i] = new GroupLesson(groupLessonsResponse.getJSONObject(i));
        }
        makeSortedLesson();
    }

    private void makeSortedLesson() {
        ArrayList<GroupLesson> firstBuffer = new ArrayList<>(groupLessons.length);
        ArrayList<GroupLesson> lastBuffer = new ArrayList<>(groupLessons.length);
        for (GroupLesson lesson : groupLessons) {
            (((lesson.getISemester() & 1) == 0) ? firstBuffer : lastBuffer).add(lesson);
        }
        fistSemesterLessons = firstBuffer.toArray(new GroupLesson[firstBuffer.size()]);
        lastSemesterLessons = lastBuffer.toArray(new GroupLesson[lastBuffer.size()]);
    }

    public GroupLesson[] getGroupLessons() {
        return groupLessons;
    }

    public GroupLesson[] getGroupLessons(int semester) {
        return (semester & 1) == 0 ? fistSemesterLessons : lastSemesterLessons;
    }

    public String getStringGroupNumber() {
        return String.valueOf(getGroupNumber());
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public Student[] getStudents() {
        return students;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(groupNumber);
        builder.append(ENTER);
        for (Student student : students) {
            builder.append(student);
            builder.append(ENTER);
        }
        for (GroupLesson lesson : groupLessons) {
            builder.append(lesson);
            builder.append(ENTER);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object element) {
        return element instanceof Group && groupNumber == ((Group) element).getGroupNumber();
    }

    @Override
    public int compareTo(Group group) {
        if (groupNumber < group.getGroupNumber()) {
            return LOW;
        } else if (groupNumber > group.getGroupNumber()) {
            return UPPER;
        }
        return EQUALS;
    }
}
