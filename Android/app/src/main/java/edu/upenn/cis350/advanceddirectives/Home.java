package edu.upenn.cis350.advanceddirectives;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.advanceddirectives.data.Person;


public class Home extends AppCompatActivity {
    static Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String username = this.getIntent().getStringExtra("username");
        Home.currentUser = MainActivity.database.getPerson(username);
        String name = Home.currentUser.getFirstName() + " " + currentUser.getLastName();
        ((TextView) findViewById(R.id.name)).setText(name);
        ((TextView) findViewById(R.id.email)).setText(currentUser.getEmail());
        ((TextView) findViewById(R.id.phone)).setText(currentUser.getPhone());
        ((TextView) findViewById(R.id.address)).setText(currentUser.getAddress());
        ((TextView) findViewById(R.id.birthday)).setText(currentUser.getBirthday());
    }

    public void onPasscodeSettingsClick(View v) {
        Intent i = new Intent(this, PasscodeActivity.class);
        startActivity(i);
    }
}
