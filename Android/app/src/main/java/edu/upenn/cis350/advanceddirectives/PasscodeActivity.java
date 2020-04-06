package edu.upenn.cis350.advanceddirectives;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PasscodeActivity extends AppCompatActivity {
    private String currPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        updatePasscodeView();
    }

    public void onPasscodeChangeClick(View v) {
        String newPasscode = ((EditText) findViewById(R.id.newPasscode)).getText().toString();
        Home.currentUser.setPasscode(newPasscode);
        updatePasscodeView();
    }

    private void updatePasscodeView() {
        if (Home.currentUser.getPasscode() == null) {
            ((TextView) findViewById(R.id.currPasscodeDisplay)).
                    setText("No passcode currently!");
        } else {
            ((TextView) findViewById(R.id.currPasscodeDisplay)).
                    setText(Home.currentUser.getPasscode());
        }
    }
}
