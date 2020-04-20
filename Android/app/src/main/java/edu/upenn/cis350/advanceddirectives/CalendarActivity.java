package edu.upenn.cis350.advanceddirectives;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static edu.upenn.cis350.advanceddirectives.Home.currentUser;

public class CalendarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private TextView dateText;
    private TextView moodText;
    private String currentDate;
    private String currentMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Spinner moodSpinner = findViewById(R.id.mood_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);
        moodSpinner.setOnItemSelectedListener(this);

        dateText = findViewById(R.id.date_text);
        findViewById(R.id.open_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        moodText = findViewById(R.id.mood_text);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String m = (month < 10) ? "0" + month : "" + month;
        String d = (dayOfMonth < 10) ? "0" + dayOfMonth : "" + dayOfMonth;
        String date = "Month/Day/Year: " + m + "/" + d + "/" + year;
        if (date.length() != 26) {
            throw new RuntimeException("WE WENT INTO THE FUTURE!");
        }
        dateText.setText(date);
        StringBuilder actualDateBuilder = new StringBuilder();
        Boolean parsedThroughDesc = false;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) == ':') {
                parsedThroughDesc = true;
            }
            if (parsedThroughDesc && date.charAt(i) != ' ' && date.charAt(i) != ':') {
                actualDateBuilder.append(date.charAt(i));
            }
        }
        currentDate = actualDateBuilder.toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentMood = "Mood: " + parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onSaveMoodClick(View v) {
        StringBuilder moodBuilder = new StringBuilder();
        for (int i = 0; i < currentMood.length(); i++) {
            if (currentMood.charAt(i) == '1' || currentMood.charAt(i) == '2' || currentMood.charAt(i) == '3' ||
                    currentMood.charAt(i) == '4' || currentMood.charAt(i) == '5') {
                moodBuilder.append(currentMood.charAt(i));
            }
        }
        Toast.makeText(this, "Updated Mood for " + currentDate + " to " + moodBuilder.toString(), Toast.LENGTH_LONG).show();
        currentUser.getMoodCalendar().setMood(currentDate, currentMood);
        Home.updateDB();
    }

    public void onShowMoodClick(View v) {
        Toast.makeText(this, "Showing Mood for " + currentDate, Toast.LENGTH_LONG).show();
        if (currentUser.getMoodCalendar().getMood(currentDate) == null) {
            moodText.setText("Mood: N/A");
        } else {
            moodText.setText(currentUser.getMoodCalendar().getMood(currentDate));
        }
    }
}
