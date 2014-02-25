package ru.journal.fspoPrj.journal.data_get_managers.groups;

import org.json.JSONArray;
import org.json.JSONException;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.io.Serializable;
import java.util.*;

public class Group implements Comparable<Group>, Serializable {
    // TODO refactoring

    private static final int LOW = -1;
    private static final int UPPER = 1;
    private static final int EQUALS = 0;
    private static final int DEFAULT_SEMESTER_COUNT = 7;

    private final int groupNumber;
    private int groupID;

    private Student[] students;
    private GroupLesson[] groupLessons;
    private HashMap<Integer, GroupLesson[]> sGroupLesson;

    public Group(int groupNumber, JSONArray studentsList) {
        this.groupNumber = groupNumber;
        this.sGroupLesson = new HashMap<>(DEFAULT_SEMESTER_COUNT);
        makeStudents(studentsList);
    }

    public Group() {
        this.groupNumber = 0;
    }

    public boolean isEmpty() {
        return (groupNumber == 0);
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
        makeGroupID(groupLessonsResponse);
        for (int i = 0; i < groupLessons.length; i++) {
            groupLessons[i] = new GroupLesson(groupLessonsResponse.getJSONObject(i));
        }
        makeSortedLesson();
    }

    private void makeSortedLesson() {
        sGroupLesson = new HashMap<>(DEFAULT_SEMESTER_COUNT);
        ArrayList<GroupLesson> buffer = new ArrayList<>(groupLessons.length);

        for (int i = 0; i < groupLessons.length; i++) {
            if (sGroupLesson.containsKey(groupLessons[i].getSemester())) continue;
            for (int j = i, semester = groupLessons[i].getSemester(); j < groupLessons.length; j++) {
                if (groupLessons[j].getSemester() == semester) {
                    buffer.add(groupLessons[j]);
                }
            }
            sGroupLesson.put(groupLessons[i].getSemester(), buffer.toArray(new GroupLesson[buffer.size()]));
            buffer.clear();
        }
    }

    public boolean hasTeacherLessons(TeacherLessons teacherlessons) {
        ArrayList<GroupLesson> bufferedLessons = new ArrayList<>(Arrays.asList(groupLessons));
        for (Iterator<GroupLesson> iterator = bufferedLessons.iterator(); iterator.hasNext(); ) {
            lookOn(teacherlessons, iterator);
        }
        return bufferStateIsGood(bufferedLessons);
    }

    private boolean bufferStateIsGood(ArrayList<GroupLesson> bufferedLessons) {
        groupLessons = bufferedLessons.toArray(new GroupLesson[bufferedLessons.size()]);
        if (bufferedLessons.size() > 0) {
            makeSortedLesson();
            return true;
        }
        return false;
    }

    private void lookOn(TeacherLessons teacherlessons, Iterator<GroupLesson> groupLesson) {
        for (TeacherLessons.TeacherLesson teacherLesson : teacherlessons.getLessons()) {
            if (!groupLesson.next().equalsTeacher(teacherLesson)) {
                groupLesson.remove();
            }
        }
    }

    public GroupLesson[] getGroupLessons() {
        return groupLessons;
    }

    public GroupLesson[] getGroupLessons(int semester) {
        return sGroupLesson.containsKey(semester) ? sGroupLesson.get(semester) : new GroupLesson[0];
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

    public int getGroupID() {
        return groupID;
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

    private void makeGroupID(JSONArray groupLessonsResponse) throws JSONException {
        if (groupLessonsResponse.length() > 0) {
            this.groupID = new GroupLesson(groupLessonsResponse.getJSONObject(0)).getGroupID();
        }
    }

    public int getFirstPossiblySemester() {
        return makeSortedSemesters()[0];
    }

    public Integer[] getAllSemesters() {
        return makeSortedSemesters();
    }

    private Integer[] makeSortedSemesters() {
        Integer[] semesters = sGroupLesson.keySet().toArray(new Integer[sGroupLesson.size()]);
        Arrays.sort(semesters);
        return semesters;
    }
}
