package com.sirius.aar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sirius.slib.SimpleNumberView;


public class MainActivity extends AppCompatActivity implements SimpleNumberView.OnChangeNumberListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SimpleNumberView) findViewById(R.id.mBtn)).SetOnChangeNumberListener(this);


    }

    @Override
    public void number(int indexNum) {
        Toast.makeText(this,indexNum+"",Toast.LENGTH_SHORT).show();
    }
}
