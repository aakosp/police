package com.psb.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.psb.R;
import com.psb.entity.OfficeInfo;
import com.psb.ui.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/1.
 */
public class OfficeAdapter extends BaseAdapter implements View.OnClickListener {

    private List<OfficeInfo> officeInfoList;
    private Context context;

    public OfficeAdapter(Context context){
        this.context = context;
        officeInfoList = new ArrayList<>();
    }

    public void setOfficeInfoList(List<OfficeInfo> infos){
        officeInfoList.clear();
        officeInfoList.addAll(infos);
    }

    public void addOfficeInfo(List<OfficeInfo> infos){
        officeInfoList.addAll(infos);
    }

    @Override
    public int getCount() {
        return officeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return officeInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OfficeViewHolder officeViewHolder = null;
        if(null == convertView){
            convertView = View.inflate(parent.getContext(), R.layout.item_office_info, null);
            officeViewHolder = new OfficeViewHolder();
            officeViewHolder.name = (TextView) convertView.findViewById(R.id.name);
            officeViewHolder.tel = (TextView) convertView.findViewById(R.id.tel);
            officeViewHolder.addr = (TextView) convertView.findViewById(R.id.addr);
            officeViewHolder.phone = (Button) convertView.findViewById(R.id.phone);
            officeViewHolder.map = (Button) convertView.findViewById(R.id.map);
            officeViewHolder.phone.setOnClickListener(this);
            officeViewHolder.map.setOnClickListener(this);
            convertView.setTag(officeViewHolder);
        }
        else{
            officeViewHolder = (OfficeViewHolder) convertView.getTag();
        }

        OfficeInfo info = this.officeInfoList.get(position);
        officeViewHolder.name.setText(info.getName());
        officeViewHolder.tel.setText(info.getTel());
        officeViewHolder.addr.setText(info.getAddr());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phone:
                ToastUtil.showLongToast(this.context, "phone click", 0);
                break;

            case R.id.map:
                ToastUtil.showLongToast(this.context, "map click", 0);
                break;
        }

    }

    private static class OfficeViewHolder{
        public TextView name, tel, addr;
        public Button phone, map;
    }
}
