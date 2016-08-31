package me.ewriter.bangumitv.dailyOnAir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.ewriter.bangumitv.R;

public class HomeActivity extends AppCompatActivity {

    private DailyOnAirPresenter mDailyOnAirPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
