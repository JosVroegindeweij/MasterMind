package com.spaceprograms.mastermind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spaceprograms.mastermind.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(getApplicationContext(), PlayActivity.class));

        findViewById(R.id.playBtn).setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), PlayActivity.class)));

        findViewById(R.id.settingsBtn).setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), SettingsActivity.class)));

        findViewById(R.id.tutorialBtn).setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), TutorialActivity.class)));
    }
}
