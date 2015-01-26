package com.psb.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.psb.R;

/**
 * Created by Orgtec on 2015/1/5.
 */
public class AlertMenu {
    private AlertMenuItemClickListener alertMenuItemClickListener;
    private String title;
    private String menuItem[] = new String[0];
    private Context context;
    private Dialog dialog;
    private TextView titleTv;
    private ListView menuListView;


    public AlertMenu(Context context,String title,String... menuItem){
        this.context = context;
        this.title = title;
        this.menuItem = menuItem;
        init();
    }

    public void setTitle(String title){
        if(titleTv != null){
            titleTv.setText(title);
        }
    }

    private void init(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_alert_menu,null);
        titleTv = (TextView) view.findViewById(R.id.titleTV);
        menuListView = (ListView) view.findViewById(R.id.menuListView);
        titleTv.setText(title);
        if(title.length()>17){
            titleTv.setGravity(Gravity.CENTER);
        }

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return menuItem.length;
            }

            @Override
            public Object getItem(int position) {
                return menuItem[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(context).inflate(R.layout.widget_alert_item,null);
                TextView alertBtn = (TextView) view.findViewById(R.id.alertBtn);
                alertBtn.setText(menuItem[position]);
                if(title.length()>16){
                    alertBtn.setGravity(Gravity.CENTER);
                }
                if(position == menuItem.length -1){
                    ((View)view.findViewById(R.id.v)).setVisibility(View.GONE);
                }
                return view;
            }
        };

        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if(alertMenuItemClickListener != null){
                    alertMenuItemClickListener.onItemClick(position);
                }
            }
        });

        dialog.setContentView(view);

    }

    public void show(){
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    public void setOnItemClickListener(AlertMenuItemClickListener alertMenuItemClickListener){
        this.alertMenuItemClickListener = alertMenuItemClickListener;
    }

    public interface  AlertMenuItemClickListener{
        void onItemClick(int position);
    }
}
