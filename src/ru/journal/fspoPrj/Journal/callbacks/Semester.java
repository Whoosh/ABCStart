package ru.journal.fspoPrj.journal.callbacks;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class Semester extends TextView {

    public static final String SAVED_KEY = "sv_ind_k";

    private static int indexer;
    private String selected;

    public Semester(Context context) {
        super(context);
        initState(0);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void click() {
        if (++indexer >= ElementStates.values().length) {
            indexer = 0;
        }
        initState(indexer);
    }

    public String getSelected() {
        return selected;
    }

    public int getIndexer() {
        return indexer;
    }

    public void setIndexer(int indexer) {
        Semester.indexer = indexer;
        initState(indexer);
    }

    private void initState(int indexer) {
        selected = ElementStates.values()[indexer].getState();
        setText(selected);
    }

    private enum ElementStates {

        FIRST("Семестр 1"),
        LAST("Семестр 2");

        private final String state;

        private ElementStates(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

}
