package com.sirius.aar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sirius.*;
import com.sirius.slib.SimpleImageViewCircleBitmap;
import com.sirius.slib.SimpleToast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleToast.makeText(this,"测试", Toast.LENGTH_SHORT).show();

    }
}
