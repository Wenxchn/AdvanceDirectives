package edu.upenn.cis350.advanceddirectives;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import edu.upenn.cis350.advanceddirectives.data.BasicDatabase;
import edu.upenn.cis350.advanceddirectives.data.Database;

public class MainActivity extends AppCompatActivity{
    static Database database = new BasicDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginButtonClick(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void onSignupButtonClick(View view) {
        startActivity(new Intent(this, Signup.class));
    }

    public void onMapButtonClick(View view) {
        startActivity(new Intent(this, FindNearestHospital.class));
    }
}
