package com.psb.ui.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 统一Toast显示位置（居中显示）
 */
public class ToastUtil {

    private static Toast toast = null;

    /**
     * LENGTH_SHORT短时间显示Toast
     * @param context 上下文
     * @param str 文本类型的内容，不用此参数则置为null
     * @param resId 资源类型的内容，不用此参数则置为0
     */
    public static void showToast(Context context,String str,int resId){
        if(toast != null){
            if(str == null){
                toast.setText(resId);
            }else{
                toast.setText(str);
            }
        }else{
            if(str == null){
                toast = Toast.makeText(context,resId,Toast.LENGTH_SHORT);
            }else{
                toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
            }
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * LENGTH_LONG长时间显示Toast
     * @param context 上下文
     * @param str 文本类型的内容，不用此参数则置为null
     * @param resId 资源类型的内容，不用此参数则置为0
     */
    public static void showLongToast(Context context,String str,int resId){
        if(toast != null){
            if(str == null){
                toast.setText(resId);
            }else{
                toast.setText(str);
            }
        }else{
            if(str == null){
                toast = Toast.makeText(context,resId,Toast.LENGTH_LONG);
            }else{
                toast = Toast.makeText(context,str,Toast.LENGTH_LONG);
            }
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
