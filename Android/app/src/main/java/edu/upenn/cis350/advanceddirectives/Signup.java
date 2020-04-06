package edu.upenn.cis350.advanceddirectives;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.advanceddirectives.data.Database;
import edu.upenn.cis350.advanceddirectives.data.Person;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signupSubmitButtonClick(View view) {
        Database db = MainActivity.database;
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.passowrd)).getText().toString();
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
        String address = ((EditText) findViewById(R.id.address)).getText().toString();
        if (db.isAvailable(username)) {
            Person currentUser = new Person(username, password,
                    name, email, phone, address);
            db.addPerson(currentUser);
            Toast.makeText(this, "Account created, you can now log in!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        } else {
            String message = "We're sorry, this username is already in use, " +
                    "please choose another username!";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
