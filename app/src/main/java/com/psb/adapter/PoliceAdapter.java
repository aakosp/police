package com.psb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.OfficeInfo;
import com.psb.entity.PoliceInfo;
import com.psb.ui.activity.ActivityMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/8.
 */
public class PoliceAdapter extends BaseAdapter implements View.OnClickListener {

    List<PoliceInfo> policeInfos = new ArrayList<>();
    private Context context;

    public PoliceAdapter(Context context) {
        this.context = context;
    }

    public void setPoliceInfos(List<PoliceInfo> policeInfos) {
        if (null == policeInfos) {
            return;
        }
        this.policeInfos.clear();
        this.policeInfos.addAll(policeInfos);
    }

    @Override
    public int getCount() {
        return policeInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return policeInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoliceViewHolder policeViewHolder = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_police, null);
            policeViewHolder = new PoliceViewHolder();
            policeViewHolder.name = (TextView) convertView.findViewById(R.id.name);
            policeViewHolder.tel = (TextView) convertView.findViewById(R.id.tel);
            policeViewHolder.tel.setOnClickListener(this);
            policeViewHolder.addr = (TextView) convertView.findViewById(R.id.addr);
            policeViewHolder.addr.setOnClickListener(this);
            policeViewHolder.office = (TextView) convertView.findViewById(R.id.office_name);
            policeViewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(policeViewHolder);
        } else {
            policeViewHolder = (PoliceViewHolder) convertView.getTag();
        }

        PoliceInfo info = this.policeInfos.get(position);
        policeViewHolder.name.setText(info.getPolice().getPolice_name());
        policeViewHolder.tel.setText(info.getPolice().getPhone());
        policeViewHolder.tel.setTag(info.getPolice().getPhone());
        policeViewHolder.addr.setText(info.getAddress().getName());
        policeViewHolder.office.setText(info.getPolice().getPolice_station_id() + "警务室");
        policeViewHolder.office.setTag(info.getPolice().getPolice_station_id());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.office_name:
                intent = new Intent();
                intent.putExtra("id", (int) v.getTag());
                intent.setClass(this.context, ActivityMap.class);
                break;
            case R.id.tel:
                Uri telUri = Uri.parse("tel:" + v.getTag());
                intent = new Intent(Intent.ACTION_DIAL, telUri);
                break;
        }
        if (null != intent) {
            this.context.startActivity(intent);
        }
    }

    private static class PoliceViewHolder {
        public TextView addr, name, office, tel;
        public ImageView img;
    }
}
