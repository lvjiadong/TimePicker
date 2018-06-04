package com.cnm.timepicker;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cnm.timepicker.adapter.MyWheelAdapter;
import com.cnm.timepicker.util.BottomDialog;
import com.cnm.timepicker.util.ToastUtil;
import com.wx.wheelview.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button bten;
    private BottomDialog bottomDialog;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bten = findViewById(R.id.bten);
        bten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogtime();
            }
        });
    }

    private void dialogtime() {
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#1A1A1A");//选中字体颜色
        style.textColor = Color.parseColor("#ABABAB");//未选中字体颜色


        View out_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_time, null);
        //日期滚轮
        final WheelView start_year = out_view.findViewById(R.id.start_year);
        final WheelView start_month = out_view.findViewById(R.id.start_month);
        final WheelView start_day = out_view.findViewById(R.id.start_day);
        final WheelView end_year = out_view.findViewById(R.id.end_year);
        final WheelView end_month = out_view.findViewById(R.id.end_month);
        final WheelView end_day = out_view.findViewById(R.id.end_day);
        TextView tv_ok = out_view.findViewById(R.id.tv_ok);
        TextView tv_cancel = out_view.findViewById(R.id.tv_cancel);
        start_year.setStyle(style);
        start_month.setStyle(style);
        start_day.setStyle(style);
        end_year.setStyle(style);
        end_month.setStyle(style);
        end_day.setStyle(style);

        // 格式化当前时间，并转换为年月日整型数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String[] split = sdf.format(new Date()).split("-");
        int currentYear = Integer.parseInt(split[0]);
        int currentMonth = Integer.parseInt(split[1]);
        int currentDay = Integer.parseInt(split[2]);
        //开始时间
        start_year.setWheelAdapter(new MyWheelAdapter(MainActivity.this));
        start_year.setWheelData(getYearData(currentYear));
        start_year.setSelection(0);
        start_month.setWheelAdapter(new MyWheelAdapter(MainActivity.this));
        start_month.setWheelData(getMonthData());
        start_month.setSelection(currentMonth - 1);
        start_day.setWheelAdapter(new MyWheelAdapter(MainActivity.this));
        start_day.setWheelData(getDayData(getLastDay(currentYear, currentMonth)));
        start_day.setSelection(currentDay - 1);
        //结束时间
        end_year.setWheelAdapter(new MyWheelAdapter(MainActivity.this));
        end_year.setWheelData(getYearData(currentYear));
        end_year.setSelection(0);
        end_month.setWheelAdapter(new MyWheelAdapter(MainActivity.this));
        end_month.setWheelData(getMonthData());
        end_month.setSelection(currentMonth - 1);
        end_day.setWheelAdapter(new MyWheelAdapter(MainActivity.this));
        end_day.setWheelData(getDayData(getLastDay(currentYear, currentMonth)));
        end_day.setSelection(currentDay - 1);


        //确定
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                String start_time;
                String end_time;
                Object startyear1 = start_year.getSelectionItem();
                Object startmonth1 = start_month.getSelectionItem();
                Object startday1 = start_day.getSelectionItem();
                Object endyear1 = end_year.getSelectionItem();
                Object endmonth1 = end_month.getSelectionItem();
                Object endday1 = end_day.getSelectionItem();
                int startyear = Integer.valueOf(String.valueOf(startyear1));
                int startmonth = Integer.valueOf(String.valueOf(startmonth1));
                int startday = Integer.valueOf(String.valueOf(startday1));
                String startmonth2;
                if (startmonth < 10) {
                    startmonth2 = "0" + String.valueOf(+startmonth);
                } else {
                    startmonth2 = String.valueOf(startmonth);
                }
                String startday2;
                if (startday < 10) {
                    startday2 = "0" + String.valueOf(startday);
                } else {
                    startday2 = String.valueOf(startday);
                }
                int endyear = Integer.valueOf(String.valueOf(endyear1));
                int endmonth = Integer.valueOf(String.valueOf(endmonth1));
                int endday = Integer.valueOf(String.valueOf(endday1));
                String endmonth2;
                if (endmonth < 10) {
                    endmonth2 = "0" + String.valueOf(endmonth);
                } else {
                    endmonth2 = String.valueOf(endmonth);
                }
                String endday2;
                if (endday < 10) {
                    endday2 = "0" + String.valueOf(endday);
                } else {
                    endday2 = String.valueOf(endday);
                }

                if (endyear - startyear == 0) {
                    if (endmonth - startmonth <= 2 && endmonth - startmonth >= 0) {
                        if (endmonth - startmonth == 0) {
                            if (endday - startday >= 0) {
                                start_time = startyear + "/" + startmonth2 + "/" + startday2;
                                
                                end_time = endyear + "/" + endmonth2 + "/" + endday2;
                              
                                Log.e(TAG, "starttime: " + start_time + "/n" + "endtime:" + end_time);
                                
                            } else {
                                ToastUtil.showToast(MainActivity.this, "当月起始日期不能大于结束日期");
                            }
                        } else {
                            start_time = startyear + "/" + startmonth2 + "/" + startday2;
                            
                            end_time = endyear + "/" + endmonth2 + "/" + endday2;
                         
                            Log.e(TAG, "starttime: " + start_time + "/n" + "endtime:" + end_time);
                            
                        }
                    } else if (endmonth < startmonth) {
                        ToastUtil.showToast(MainActivity.this, "起始时间不能大于结束时间");
                    } else {
                        ToastUtil.showToast(MainActivity.this, "请选择两个月");
                    }
                } else {
                    ToastUtil.showToast(MainActivity.this, "请输入正确的年份");
                }
            }
        });
        //取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        //防止弹出两个窗口
        if (bottomDialog != null && bottomDialog.isShowing()) {
            return;
        }
        bottomDialog = new BottomDialog(MainActivity.this, R.style.ActionSheetDialogStyle);
        //将布局设置给Dialog
        bottomDialog.setContentView(out_view);
        bottomDialog.show();//显示对话框

    }

    //设置年
    private ArrayList<String> getYearData(int currentYear) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = currentYear; i >= 1900; i--) {
            list.add(String.valueOf(i));
        }
        return list;
    }


    //月
    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    //日
    private ArrayList<String> getDayData(int lastDay) {
        //ignore condition
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= lastDay; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    /**
     * 判断是否闰年
     *
     * @param year
     * @return
     */
    private boolean isLeapYear(int year) {
        return (year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * 获取特定年月对应的天数
     *
     * @param year
     * @param month
     * @return
     */
    private int getLastDay(int year, int month) {
        if (month == 2) {
            // 2月闰年的话返回29，防止28
            return isLeapYear(year) ? 29 : 28;
        }
        // 一三五七八十腊，三十一天永不差
        return month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ? 31 : 30;
    }

}
