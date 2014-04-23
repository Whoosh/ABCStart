package ru.journal.fspoPrj.search_users.profile.elements;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLesson;
import ru.journal.fspoPrj.public_code.MailSender;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.profile.config.Config;


public class InformerFragment extends Fragment implements View.OnClickListener {

    private static final String STUDENT = "Студент";
    private static final String STATUS = "Статус : ";
    private static final String TEACHER = "Преподаватель";
    private static final String GROUP = "Группа : ";
    private static final String MAIL = "Почта : ";
    private static final String PHONE = "Телефон : ";
    private static final String TEACHER_AKA_STUDENT = "Студент/Преподаватель";
    private static final String SEMESTER = "Семестр";
    private static final String LESSONS = "Предметы :";
    private static final String EMPTY = "";
    private static final String SPACE = " ";

    private static final int CL_HOLO = Color.parseColor("#33b5e5");

    private LinearLayout scrolledLayout;
    private ProfileInfo userInfo;
    private Activity parent;
    private LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initElements();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return layout;
    }

    public void showInfo(ProfileInfo userInfo) {
        this.userInfo = userInfo;
        setInfoNames();
    }

    private void initElements() {
        this.parent = getActivity();
        this.layout = new LinearLayout(parent);
        layout.addView(new VerticalLine(parent, Color.TRANSPARENT, Config.getSeparateTransparentNamesLineWidth()));
        scrolledLayout = new LinearLayout(parent);
        ScrollView scroller = new ScrollView(parent);

        scrolledLayout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.TRANSPARENT);
        layout.setGravity(Gravity.CENTER);
        scroller.addView(scrolledLayout);
        layout.addView(scroller);
    }

    private void setInfoNames() {
        if (userInfo != null) {
            addNames();
            addStatus();
            addLessons();
            addTransparentHorizontalLine();
            addBoldLogicSeparateLine();
            addContacts();
        }
    }

    private void addLessons() {
        try {
            if (userInfo.getLessons().length > 0) {
                addTransparentHorizontalLine();
                addBoldLogicSeparateLine();
                addTransparentHorizontalLine();
                addLesson(LESSONS);
                addTransparentHorizontalLine();
                for (TeacherLesson lesson : userInfo.getLessons()) {
                    addLesson(lesson.getName() + SPACE + lesson.getSemester() + SPACE + SEMESTER);
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void addLesson(String title) {
        addText(title);
        addGrayLine();
    }

    private void addGrayLine() {
        scrolledLayout.addView(new HorizontalLine(parent, Color.GRAY, Config.getCYANHorizontalLineHeight()));
    }

    private void addText(String text) {
        TextView textElement = new TextView(parent);
        textElement.setText(text);
        textElement.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textElement.setTypeface(Typeface.SANS_SERIF);
        scrolledLayout.addView(textElement);
    }

    private void addStatus() {
        if (userInfo.isStudent()) {
            addName(STATUS + STUDENT);
            addName(GROUP + userInfo.getStringGroup());
        } else if (userInfo.isTeacher()) {
            addName(STATUS + TEACHER);
        } else {
            addName(STATUS + TEACHER_AKA_STUDENT);
        }
    }

    private void addContacts() {
        if (!userInfo.getMail().isEmpty()) {
            addContact(MAIL + userInfo.getMail());
        }
        if (!userInfo.getPhone().isEmpty()) {
            addContact(PHONE + userInfo.getPhone());
        }
        addTransparentHorizontalLine();
        addBoldLogicSeparateLine();
        addTransparentHorizontalLine();
    }

    private void addContact(String text) {
        addTransparentHorizontalLine();
        addCYANLine();
        addClickedContact(text);
        addCYANLine();
    }

    private void addClickedContact(String text) {
        scrolledLayout.addView(new ContactElement(parent, text, this));
    }

    private void addNames() {
        addTransparentHorizontalLine();
        addName(userInfo.getPointedFirstName());
        addName(userInfo.getPointedMiddleName());
        addName(userInfo.getPointedLastName());
    }

    private void addBoldLogicSeparateLine() {
        scrolledLayout.addView(new HorizontalLine(parent, Color.BLACK, Config.getBoldLogicHorizontalSeparateLineHeight()));
    }

    private void addName(String text) {
        addText(text);
        addCYANLine();
    }

    private void addTransparentHorizontalLine() {
        scrolledLayout.addView(new HorizontalLine(parent, Color.TRANSPARENT, Config.getSeparateTransparentNamesLineHeight()));
    }

    private void addCYANLine() {
        scrolledLayout.addView(new HorizontalLine(parent, CL_HOLO, Config.getCYANHorizontalLineHeight()));
    }


    @Override
    public void onClick(View view) {
        if (view instanceof ContactElement) {
            handleContactClick((ContactElement) view);
        }
    }

    private void handleContactClick(ContactElement element) {
        String elementData = element.getData();
        if (elementData.equals(MAIL + userInfo.getMail())) {
            MailSender.mailToUser(parent, userInfo.getMail());
        } else if (elementData.equals(PHONE + userInfo.getPhone())) {
            new AskSaveFragment(userInfo).show(getFragmentManager(), EMPTY);
        }
    }
}
