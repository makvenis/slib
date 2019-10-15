package com.sirius.aar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sirius.slib.CountDownView;
import com.sirius.slib.SimpleToast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownView view = (CountDownView) findViewById(R.id.mStopTimer);
        view.setOnStopTimer(new CountDownView.OnClinkStopTimer() {
            @Override
            public void stop() {
                SimpleToast.makeText(MainActivity.this,"我点击了让倒计时结束", Toast.LENGTH_LONG).show();
            }

            @Override
            public void over() {
                SimpleToast.makeText(MainActivity.this,"自动结束", Toast.LENGTH_LONG).show();
            }
        });
    }

}
