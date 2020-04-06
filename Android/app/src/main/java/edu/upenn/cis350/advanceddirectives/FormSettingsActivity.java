package edu.upenn.cis350.advanceddirectives;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.upenn.cis350.advanceddirectives.data.Form;

public class FormSettingsActivity extends AppCompatActivity {
    private int currQuestIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_settings);
        this.currQuestIdx = -1;
    }

    private void displayResponseText() {
        Form.Question currQuestion = Home.currentUser.getForm().getQuestion(currQuestIdx);
        EditText responseDisplay = findViewById((R.id.currResponseText));
        if (currQuestion.getResponse() == null) {
            responseDisplay.setText("The question continues on the next one!");
        } else {
            responseDisplay.setText(currQuestion.getResponse());
        }
    }

    private void updateQuestionViews() {
        TextView questionDisplay = findViewById(R.id.currQuestionText);
        Form.Question currQuestion = Home.currentUser.getForm().getQuestion(currQuestIdx);
        questionDisplay.setText(currQuestion.getQuestion());

        displayResponseText();
//        EditText responseDisplay = findViewById((R.id.currResponseText));
//        if (currQuestion.getResponse() != null) {
//            responseDisplay.setText(currQuestion.getResponse());
//        } else {
//            responseDisplay.setText("");
//        }
    }

    public void onNextClick(View v) {
        if (currQuestIdx == Home.currentUser.getForm().numQuestions() - 1) {
            Toast.makeText(this, "You reached the end of the form!",
                    Toast.LENGTH_LONG).show();
        } else {
            currQuestIdx++;
            updateQuestionViews();
        }
    }

    public void onPrevClick(View v) {
        if (currQuestIdx == -1) {
            Toast.makeText(this, "Start the form by clicking next!",
                    Toast.LENGTH_LONG).show();
        } else if (currQuestIdx == 0) {
            Toast.makeText(this, "You are on the first question already!",
                    Toast.LENGTH_LONG).show();
        } else {
            currQuestIdx--;
            updateQuestionViews();
        }
    }

    public void onSubmitClick(View v) {
        if (currQuestIdx < Home.currentUser.getForm().numQuestions() && currQuestIdx > -1) {
            EditText currResponse = findViewById(R.id.currResponseText);
            String response = currResponse.getText().toString();
            Form.Question currQuestion = Home.currentUser.getForm().getQuestion(currQuestIdx);
            if (currQuestion.getResponse() != null) {
                currQuestion.setResponse(response);
                Toast.makeText(this, "Response Saved!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Question continues next!",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Start the form by clicking next!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
