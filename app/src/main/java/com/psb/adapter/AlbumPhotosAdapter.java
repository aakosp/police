package com.psb.adapter;

import java.util.ArrayList;
import java.util.List;

import com.psb.R;
import com.psb.ui.util.DisplayUtil;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.util.PhotoUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class AlbumPhotosAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Uri> urls = new ArrayList<Uri>(); // 图片链接地址集合
    private Activity context;
    private LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    private View add;
    PhotoUtil photo;

    public AlbumPhotosAdapter(Activity context, View root) {
        this.context = context;
        photo = new PhotoUtil(context, root);
        //计算宽高
        this.params.width = (DisplayUtil.getDisplayMetrics().widthPixels - DisplayUtil.dip2px(32)) / 3;
        this.params.height = this.params.width;
        add = View.inflate(context, R.layout.item_img, null);
        add.setOnClickListener(this);
    }

    public List<Uri> getImgs() {
        return urls;
    }

    public void addImage(Uri uri) {
        this.urls.add(uri);
        this.notifyDataSetChanged();
    }

    public void deleteImage(Uri uri) {
        urls.remove(uri);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = urls.size() + 1;
        if (count > 6) {
            count = 6;
        }
        return count;
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
        Log.d("convertView", "" + position + "   " + urls.size());
        if (urls.size() == 0 || position == urls.size()) {
            return add;
        }
        ImageItem item = null;
        if (convertView == null || null == convertView.getTag()) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_img, null);
            item = new ImageItem();
            item.img = (ImageView) convertView.findViewById(R.id.img);
            item.del = (ImageView) convertView.findViewById(R.id.del);
            item.del.setOnClickListener(this);
            convertView.setTag(item);
        } else {
            item = (ImageItem) convertView.getTag();
        }
//        Bitmap bitmap = ImageUtil.getBitmapFromUri(urls.get(position));
        item.img.setImageURI(urls.get(position));
        item.del.setVisibility(View.VISIBLE);
        item.del.setTag(urls.get(position));
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == add.getId()) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//            intent.setType("image/*");
//            intent.putExtra("return-data", true);
//            context.startActivityForResult(intent, 0);
//            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            context.startActivityForResult(intent, 0);
            if (null != photo) {
                photo.initPopuptWindow();
            }
        } else if (v.getId() == R.id.del) {
            Uri uri = (Uri) v.getTag();
            urls.remove(uri);
            notifyDataSetChanged();
        }
    }

    private class ImageItem {
        public ImageView img;
        public ImageView del;
    }

}
