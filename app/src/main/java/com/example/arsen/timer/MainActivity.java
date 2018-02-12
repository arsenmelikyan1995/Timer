package com.example.arsen.timer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    int millisecond;
    int second;
    int minute;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> data;
    Handler timer_handler;
    Runnable logic;
    boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.pr_tv);
        lv = (ListView) findViewById(R.id.records);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
        second = 0;
        minute = 0;
        millisecond = 0;
        setTime();
        logic = new Runnable() {
            @Override
            public void run() {
                millisecond++;
                if (millisecond >= 60) {
                    second++;
                    millisecond = 0;
                }
                if (second >= 60) {
                    minute++;
                    second = 0;
                }
                setTime();
                if (isRunning) {
                    timer_handler.postDelayed(logic, 1);
                }
            }
        };
    }

    public void updateButtons(View selectedButton) {
        LinearLayout parent = (LinearLayout) selectedButton.getParent();
        int index = parent.indexOfChild(selectedButton);
        switch (index) {
            case 0:
                selectedButton.setEnabled(false);
                parent.getChildAt(1).setEnabled(true);
                parent.getChildAt(2).setEnabled(true);
                break;

            case 1:
                selectedButton.setEnabled(false);
                parent.getChildAt(0).setEnabled(true);
                parent.getChildAt(2).setEnabled(true);
                break;

            case 2:
                selectedButton.setEnabled(false);
                parent.getChildAt(0).setEnabled(false);
                parent.getChildAt(1).setEnabled(true);
                break;
            default:
                break;

        }
    }

    public void setTime() {
        String m = minute < 10 ? "0" + minute : "" + minute;
        String s = second < 10 ? "0" + second : "" + second;
        String ms = millisecond < 10 ? "0" + millisecond : "" + millisecond;
        tv.setText(m + ":" + s + ":" + ms);
    }

    public void stop(View view) {
        updateButtons(view);
        isRunning = false;
        timer_handler.removeCallbacks(logic);
    }

    public void start(View view) {
        updateButtons(view);
        if (!isRunning) {
            isRunning = true;
            timer_handler = new Handler();
            timer_handler.post(logic);
        }

    }

    public void reset(View view) {
        updateButtons(view);
        isRunning = false;
        timer_handler.removeCallbacks(logic);
        data.add(tv.getText().toString());
        adapter.notifyDataSetChanged();
        millisecond = 0;
        second = 0;
        minute = 0;
        setTime();
    }
}

