package com.psb.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by aako on 2015/2/8.
 */
public class SimpleAdapters extends SimpleCursorAdapter {

    public SimpleAdapters(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //        //绑定数据到view ，test
        TextView name = (TextView) view.findViewById(android.R.id.text1);
        TextView message = (TextView) view.findViewById(android.R.id.text2);
        name.setText("剩余" + cursor.getString(1) + "小时" + cursor.getString(2) + "分钟");
        //        message.setText(cursor.getString(2));
        //                ItemCathe ic = new ItemCathe();
        //设置tag标记行
        view.setTag(cursor.getString(0));
        message.setText(cursor.getString(3) + view.getTag());
        super.bindView(view, context, cursor);
    }

}
