package ru.journal.fspoPrj.search_users.profile.elements;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLesson;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.profile.config.Config;

import java.util.List;

public class InformerFragment extends Fragment implements View.OnClickListener {

    private static final String FIRST_NAME = "Имя : ";
    private static final String MIDDLE_NAME = "Отчество : ";
    private static final String LAST_NAME = "Фамилия : ";
    private static final String STUDENT = "Студент";
    private static final String STATUS = "Статус : ";
    private static final String TEACHER = "Преподаватель";
    private static final String GROUP = "Группа : ";
    private static final String MAIL = "Почта : ";
    private static final String PHONE = "Телефон : ";
    private static final String SEND_MESSAGE_IN_SYSTEM = "Написать сообщение";
    private static final String TEACHER_AKA_STUDENT = "Студент/Преподаватель";
    private static final String SEMESTER = "Семестр";
    private static final String LESSONS = "Предметы :";
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String SUFFIX_GM = ".gm";
    private static final String GMAIL = "gmail";

    private static final int ALL_ACT = 0;
    private static final int DEFAULT_REQUEST = 1;
    private static final int CL_GRAY = Color.rgb(222, 222, 222);
    private static final int CL_HOLO = Color.parseColor("#33b5e5");
    private static final int BG_COLOR = Color.parseColor("#EFF1F0");

    private ScrollView scroller;
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
        layout.setBackgroundColor(CL_GRAY);
        scroller = new ScrollView(parent);
        scrolledLayout = new LinearLayout(parent);
        scrolledLayout.setOrientation(LinearLayout.VERTICAL);
        scroller.addView(scrolledLayout);
        layout.addView(scroller);
    }

    private void setInfoNames() {
        if (userInfo != null) {
            layout.setBackgroundColor(BG_COLOR);
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
        textElement.setBackgroundColor(Color.WHITE);
        textElement.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // textElement.setTextSize(Config.getExtendUserInfoNamesTextSize());
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
        addContact(SEND_MESSAGE_IN_SYSTEM);
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
        addName(FIRST_NAME + userInfo.getFirstName());
        addName(MIDDLE_NAME + userInfo.getMiddleName());
        addName(LAST_NAME + userInfo.getLastName());
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

    private void mailToUser() {
        Intent mailTo = new Intent(android.content.Intent.ACTION_SEND);
        mailTo.setType(TEXT_PLAIN);
        List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(mailTo, ALL_ACT);
        ResolveInfo selectedActivity = null;
        for (ResolveInfo activityInfo : matches) {
            if (activityInfo.activityInfo.packageName.endsWith(SUFFIX_GM) || activityInfo.activityInfo.name.toLowerCase().contains(GMAIL)) {
                selectedActivity = activityInfo;
            }
        }
        if (selectedActivity != null) {
            mailTo.setClassName(selectedActivity.activityInfo.packageName, selectedActivity.activityInfo.name);
            mailTo.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{userInfo.getMail()});
            startActivityForResult(mailTo, DEFAULT_REQUEST);
        } else {
            Logger.printError(new IllegalArgumentException(), getClass());
        }
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
            mailToUser();
        } else if (elementData.equals(PHONE + userInfo.getPhone())) {
            new AskSaveFragment(userInfo).show(getFragmentManager(), EMPTY);
        } else if (elementData.equals(SEND_MESSAGE_IN_SYSTEM)) {
            // TODO Message in system
            System.out.println("Message in system");
        }
    }
}
