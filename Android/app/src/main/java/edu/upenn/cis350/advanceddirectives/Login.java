package edu.upenn.cis350.advanceddirectives;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.advanceddirectives.data.Database;
import edu.upenn.cis350.advanceddirectives.data.Person;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginSubmitButtonClick(View view) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.passowrd)).getText().toString();
        Database db = MainActivity.database;
        Person person = db.getPerson(username);
        if (person != null && person.getPassword().equals(password)) {
            Toast.makeText(this, "Logging In!",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Home.class);
            i.putExtra("username", username);
            startActivity(i);
        } else {
            Toast.makeText(this, "Username or Password is incorrect, please try again.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
