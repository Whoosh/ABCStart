package ru.journal.fspoPrj.journal.callbacks;

import ru.journal.fspoPrj.journal.EditJournalActivity;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.journal.StudentCheckerActivity;

public enum JournalTabs {

    LOOKING_JOURNAL_TAB("Просмотр", LookingJournalActivity.class),
    EDIT_JOURNAL_TAB("Редактирование", EditJournalActivity.class),
    CHECK_STUDENT_TAB("Отметить студентов", StudentCheckerActivity.class),
    SOME_MORE("Что-то ещё", LookingJournalActivity.class);

    private final Tab journalTab;

    private JournalTabs(String classToolName, Class<?> classTab) {
        journalTab = new Tab(classTab, classToolName);
    }

    public String getClassToolName() {
        return journalTab.getName();
    }

    public Class<?> getClassTab() {
        return journalTab.getClassTab();
    }

    public int getTabID() {
        return journalTab.getTabID();
    }

    private class Tab {
        private Class<?> classTab;
        private String classToolName;

        public Tab(Class<?> classTab, String classToolName) {
            this.classTab = classTab;
            this.classToolName = classToolName;
        }

        public String getName() {
            return classToolName;
        }

        public Class<?> getClassTab() {
            return classTab;
        }

        public int getTabID() {
            return ordinal();
        }
    }
}
