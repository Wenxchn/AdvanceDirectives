package edu.upenn.cis350.advanceddirectives;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.advanceddirectives.data.Person;


public class Home extends AppCompatActivity {
    private Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String username = this.getIntent().getStringExtra("username");
        this.currentUser = MainActivity.database.getPerson(username);
        ((TextView) findViewById(R.id.name)).setText(currentUser.getName());
        ((TextView) findViewById(R.id.email)).setText(currentUser.getEmail());
        ((TextView) findViewById(R.id.phone)).setText(currentUser.getPhone());
        ((TextView) findViewById(R.id.address)).setText(currentUser.getAddress());
    }
}
