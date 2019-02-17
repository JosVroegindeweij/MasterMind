package com.example.josvr.mastermind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.playBtn).setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),PlayActivity.class));
            }
        });

        findViewById(R.id.settingsBtn).setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });

        findViewById(R.id.tutorialBtn).setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),TutorialActivity.class));
            }
        });
    }
}
