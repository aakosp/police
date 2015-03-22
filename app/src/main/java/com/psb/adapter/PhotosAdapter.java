package com.psb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.ui.util.DisplayUtil;
import com.psb.ui.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends BaseAdapter {

    private List<String> urls = new ArrayList<String>(); // 图片链接地址集合
    private Activity context;
    private LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    public PhotosAdapter(Activity context, List<String> imgs) {
        this.context = context;
        //计算宽高
        this.params.width = (DisplayUtil.getDisplayMetrics().widthPixels - DisplayUtil.dip2px(32)) / 3;
        this.params.height = this.params.width;
        this.urls.addAll(imgs);
    }

    public List<String> getImgs() {
        return urls;
    }

    public void addImage(String uri) {
        this.urls.add(uri);
        this.notifyDataSetChanged();
    }

    public void deleteImage(Uri uri) {
        urls.remove(uri);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > urls.size()) {
            return null;
        }
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItem item = null;
        if (convertView == null || null == convertView.getTag()) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_img, null);
            item = new ImageItem();
            item.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(item);
        } else {
            item = (ImageItem) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(urls.get(position), item.img, ImageUtil.options);
        return convertView;
    }

    private class ImageItem {
        public ImageView img;
    }

}
