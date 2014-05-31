package ru.journal.fspoPrj.journal.data_get_managers.communicators;

import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;

public class LookingJournalsCommunicator extends JournalsCommunicator {

    public LookingJournalsCommunicator(JournalActivity parentCaller) {
        super(parentCaller);
    }

    @Override
    public GroupLesson getLesson(String string, Group selectedGroup, int semester) {
        for (GroupLesson lesson : selectedGroup.getGroupLessons()) {
            if (lesson.getShortName().equals(string) && lesson.getSemester() == semester) {
                return lesson;
            }
        }
        return null;
    }
}
