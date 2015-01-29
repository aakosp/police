package com.util;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    private static String pat1 = "yyyy-MM-dd HH:mm:ss";
    private static String pat3 = "MM-dd";
    private static String pat4 = "HH:mm";
    private static String pat5 = "yyyyMMdd";
    private static String pat6 = "MM-dd HH:mm";

    private static SimpleDateFormat sdf1 = new SimpleDateFormat(pat1,
            Locale.getDefault());
    private static SimpleDateFormat sdf3 = new SimpleDateFormat(pat3,
            Locale.getDefault());
    private static SimpleDateFormat sdf4 = new SimpleDateFormat(pat4,
            Locale.getDefault());
    private static SimpleDateFormat sdf5 = new SimpleDateFormat(pat5,
            Locale.getDefault());
    private static SimpleDateFormat sdf6 = new SimpleDateFormat(pat6,
            Locale.getDefault());

    public static long getMillisecond(String string) {
        long lag = 0;
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault());
            date = Date(sf.parse(string));
            lag = date.getTime();
        } catch (ParseException e) {
            Log.d("TimeUtil-farmatTime", e.getMessage());
        }
        return lag;
    }

    public static Date Date(Date date) {
        Date datetimeDate;
        datetimeDate = new Date(date.getTime());
        return datetimeDate;
    }

    /*public static String getTimeLag(String commitDate) {
        String strLag = "未知";
        if (commitDate == null || commitDate.trim().equals("")) {
            return strLag;
        }
        Date date = null;
        try {
            date = sdf1.parse(commitDate);
        } catch (ParseException e1) {
            return strLag;
        }

        long lag = System.currentTimeMillis() - getMillisecond(commitDate);
        if (lag > 60 * 60 * 60 * 24 * 1000) {
            strLag = sdf3.format(date);
        } else if (lag > 7200000) {
            try {
                strLag = sdf4.format(date);
            } catch (Exception e) {
                Log.d("getShortTime", commitDate);
            }

        } else if (lag > 3600000) {
            strLag = AppContext.getInstance().getString(R.string.time_1d);
        } else if (lag > 1800000) {
            strLag = AppContext.getInstance().getString(R.string.time_30m);
        } else if (lag > 1200000) {
            strLag = AppContext.getInstance().getString(R.string.time_20m);
        } else if (lag > 600000) {
            strLag = AppContext.getInstance().getString(R.string.time_10m);
        } else if (lag > 300000) {
            strLag = AppContext.getInstance().getString(R.string.time_5m);
        } else if (lag > 180000) {
            strLag = AppContext.getInstance().getString(R.string.time_3m);
        } else if (lag > 120000) {
            strLag = AppContext.getInstance().getString(R.string.time_2m);
        } else if (lag > 60000) {
            strLag = AppContext.getInstance().getString(R.string.time_1m);
        } else {
            strLag = AppContext.getInstance().getString(R.string.time_s);
        }
        return strLag;
    }*/

    public static String twoDateDistance(String strStartDate,
                                         String strEndDate, int n) {
        String strDate = null;
        String nowDateStr = sdf5.format(new Date());
        if (strStartDate == null || strEndDate == null) {
            return null;
        }
        try {
            Date startDate = sdf1.parse(strStartDate);
            Date endDate = sdf1.parse(strEndDate);
            String startDatestr = sdf5.format(startDate);
            String endDatestr = sdf5.format(endDate);

            if (endDatestr.endsWith(nowDateStr)) {
                if (startDatestr.equals(nowDateStr)) {
                    long timeLong = endDate.getTime() - startDate.getTime();
                    if (timeLong / (60 * 1000) > n) {
                        strDate = sdf4.format(endDate);
                    }
                } else {
                    strDate = sdf4.format(endDate);
                }
            } else {
                if (startDatestr.equals(endDatestr)) {
                    long timeLong = endDate.getTime() - startDate.getTime();
                    if (timeLong / (60 * 1000) > n) {
                        strDate = sdf6.format(endDate);
                    }
                } else {
                    strDate = sdf6.format(endDate);
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return strDate;
    }

    public static boolean checkDataDistance(String startTime, String endTime,
                                            int distance) {
        long time;
        if (startTime == null || endTime == null) {
            return true;
        }
        try {
            Date startDate = sdf1.parse(startTime);
            Date endDate = sdf1.parse(endTime);
            time = endDate.getTime() - startDate.getTime();
            return (int) (time / 1000) > distance;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String firstMsgTime(String time) {
        String strDate = null;
        if (time == null) {
            return null;
        }
        try {
            String n = sdf5.format(new Date());
            Date startDate = sdf1.parse(time);
            String startTime = sdf5.format(startDate);
            if (n.equals(startTime)) {
                strDate = sdf4.format(startDate);
            } else {
                strDate = sdf6.format(startDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return strDate;
    }

    public static String getShortTime(String date) {
        if (null == date || "".equals(date.trim())) {
            return "";
        }
        String strDate = "";
        Date startDate;
        try {
            startDate = sdf1.parse(date);
            Date nowTime = new Date();
            String tmp1 = sdf5.format(nowTime);
            String tmp2 = sdf5.format(startDate);
            if (tmp1.compareTo(tmp2) > 0) {
                strDate = sdf3.format(startDate);
            } else {
                strDate = sdf4.format(startDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String getYYMM(String date) {
        String strDate = "";
        try {
            Date startDate = sdf1.parse(date);
            strDate = sdf3.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static boolean checkSendingTimeOut(String date) {
        boolean res = false;
        try {
            Date checkdate = sdf1.parse(date);
            res = new Date().getTime() - checkdate.getTime() > 60000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public static boolean TimeLessThanNsec(Date firstDate, Date secondDate,
                                           int n) {
        if (firstDate == null || firstDate == null) {
            return false;
        }
        boolean lessThanN = false;
        long timeLong = secondDate.getTime() - firstDate.getTime();
        if (timeLong / 1000 < n) {
            lessThanN = true;
        }
        return lessThanN;
    }

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date());
    }

    public static String getYYYYMMDDDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        return format.format(new Date());
    }

}
