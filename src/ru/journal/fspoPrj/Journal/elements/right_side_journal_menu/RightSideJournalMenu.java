package ru.journal.fspoPrj.journal.elements.right_side_journal_menu;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.R;

public class RightSideJournalMenu extends LinearLayout implements View.OnClickListener {

    public static final int PLUS = 1;
    public static final int REMOVE = 2;
    public static final int EDIT = 3;

    private ImageButton plusButton;
    private ImageButton removeButton;
    private ImageButton editButton;
    private Context context;
    private JournalEditsCallBack callBack;


    public RightSideJournalMenu(Context context) {
        super(context);
        this.context = context;
        initElements();
    }

    public void setCallBack(JournalEditsCallBack callBack) {
        this.callBack = callBack;
    }

    private void initElements() {
        removeButton = new ImageButton(context);
        removeButton.setOnClickListener(this);
        removeButton.setImageResource(R.drawable.ic_remove);

        plusButton =  new ImageButton(context);
        plusButton.setOnClickListener(this);
        plusButton.setImageResource(R.drawable.ic_plus);

        editButton =  new ImageButton(context);
        editButton.setOnClickListener(this);
        editButton.setImageResource(R.drawable.ic_edit);

        setOrientation(LinearLayout.VERTICAL);

        addView(plusButton);
        addView(removeButton);
        addView(editButton);
        setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(plusButton)) {
            callBack.journalMenuClicked(PLUS);
        } else if (view.equals(removeButton)) {
            callBack.journalMenuClicked(REMOVE);
        } else if (view.equals(editButton)) {
            callBack.journalMenuClicked(EDIT);
        }
    }

    public static interface JournalEditsCallBack {
        void journalMenuClicked(int keyCode);
    }
}
