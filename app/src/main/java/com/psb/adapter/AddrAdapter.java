package com.psb.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Addr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/2/5.
 */
public class AddrAdapter extends BaseAdapter {

    private List<Addr> addrs = new ArrayList<>();

    public void setAddrs(List<Addr> adds) {
        this.addrs.clear();
        this.addrs.addAll(adds);
    }

    @Override
    public int getCount() {
        return addrs.size();
    }

    @Override
    public Object getItem(int position) {
        return addrs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView addr = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_news_noimg, null);

        }
        addr = (TextView) convertView.findViewById(R.id.title);
        Addr item = addrs.get(position);
        addr.setText(item.getName());
        return convertView;
    }
}
