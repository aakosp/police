package com.psb.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

//该类用于处理诉求图片显示不正常的情况：只能显示一行或者一行多一点，而不能显示全部的超过三张的图片
public class ImageGridView extends GridView {
	public ImageGridView(Context context) {
		super(context);
	}
	public ImageGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ImageGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expand = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expand);
	}
}
