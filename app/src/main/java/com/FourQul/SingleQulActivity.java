package com.FourQul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.guidedkeys.main.R;

public class SingleQulActivity extends AppCompatActivity {

    int index =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_qul);

        index = getIntent().getIntExtra("index",-1);
        Toast.makeText(this, "Index: "+index, Toast.LENGTH_SHORT).show();
    }
}
