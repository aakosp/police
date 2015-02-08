package com.psb.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.OfficeInfo;
import com.psb.entity.PoliceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/8.
 */
public class PoliceAdapter extends BaseAdapter {

    List<PoliceInfo> policeInfos = new ArrayList<>();

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
            policeViewHolder.addr = (TextView) convertView.findViewById(R.id.addr);
            policeViewHolder.office = (TextView) convertView.findViewById(R.id.office_name);
            policeViewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(policeViewHolder);
        } else {
            policeViewHolder = (PoliceViewHolder) convertView.getTag();
        }

        PoliceInfo info = this.policeInfos.get(position);
        policeViewHolder.name.setText(info.getPolice().getName());
        policeViewHolder.tel.setText(info.getPolice().getPhone());
        policeViewHolder.addr.setText(info.getAddr().getName());
        policeViewHolder.office.setText(info.getAddr().getName() + "警务室");
        ;
        return convertView;
    }

    private static class PoliceViewHolder {
        public TextView addr, name, office, tel;
        public ImageView img;
    }
}
