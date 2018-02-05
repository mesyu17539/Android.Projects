package com.bitcamp.app.scajuller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String date=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton raBu1=findViewById(R.id.setDate);
        RadioButton raBu2=findViewById(R.id.setTime);
        Button reserv=findViewById(R.id.reserv);
        final CalendarView cadi=findViewById(R.id.cadi);
        final TimePicker selectTime=findViewById(R.id.selecttime);
        TextView today=findViewById(R.id.today);
        final TextView year =findViewById(R.id.year);
        final TextView month =findViewById(R.id.month);
        final TextView day =findViewById(R.id.day);
        final TextView hour =findViewById(R.id.hour);
        final TextView minit =findViewById(R.id.second);
        selectTime.setVisibility(View.INVISIBLE);



        String nowtime= DateFormat.getDateTimeInstance().format(new Date());
        today.setText(nowtime);



        raBu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadi.setVisibility(View.VISIBLE);
                selectTime.setVisibility(View.INVISIBLE);
            }
        });
        raBu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadi.setVisibility(View.INVISIBLE);
                selectTime.setVisibility(View.VISIBLE);
            }
        });
        reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr=date.split("-");
                year.setText(arr[0]);
                month.setText(arr[1]);
                day.setText(arr[2]);
                hour.setText(String.valueOf(selectTime.getHour()));
                minit.setText(String.valueOf(selectTime.getMinute()));
            }
        });
        cadi.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView caldView, int year, int month, int day) {
                date =  (year + "-" + (month+1)+"-"+day);
            }
        });
    }
}
