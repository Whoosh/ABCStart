package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.HorizontalLine;
import com.example.First_prj.Journal.DataHead.DataHeadSelector;
import com.example.First_prj.Journal.DataHead.InfoHead;

public class MainConstructor extends LinearLayout {

    DataHeadSelector dataHeadSelector;

    public MainConstructor(Context context) {
        super(context);
        dataHeadSelector = new DataHeadSelector(context);
        super.setOrientation(VERTICAL);
        super.addView(new InfoHead(context));
        super.addView(new HorizontalLine(context, Color.CYAN, 2));
        super.addView(dataHeadSelector);
        super.addView(new HorizontalLine(context, Color.CYAN,2));
        //@TODO фамилии и  таблицу.
    }

}
