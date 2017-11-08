package com.kizxm.kirakiradraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Extras extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.drawButton) ImageButton mDrawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDrawButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mDrawButton) {
            Toast.makeText(Extras.this, "Can't draw yet", Toast.LENGTH_LONG).show();
        }
    }
}

