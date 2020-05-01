package edu.upenn.cis350.advanceddirectives.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Form {

    public static class Question {
        private String question;
        private String response;

        public Question(String question, String response) {
            this.question = question;
            this.response = response;
        }

        public Question(String question) {
            this.question = question;
//            this.response = "";
        }

        public String getQuestion() {
            return this.question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getResponse() {
            return this.response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    List<Question> questionList;

    public Form() {
        this.questionList = new ArrayList<>();
        this.fillFormWithQuestions();
    }

    private String delimeter = "`~!@#Next_Answer#@!~`";

    public String getAnswers() {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numQuestions(); i++) {
            ans.append(getQuestion(i).getResponse());
            if (i != numQuestions() - 1) {
                ans.append(delimeter);
            }
        }
        return ans.toString();
    }

    public void setFormFromAnswers(String answers) {
        String[] ans = answers.split(delimeter, numQuestions());
        if (ans.length != numQuestions()) {
            System.out.println("questions don't line up");
            return;
        }
        for (int i = 0; i < ans.length; i++) {
            if (!ans[i].equals("null")) {
                getQuestion(i).setResponse(ans[i]);
            }
        }
    }

    private void fillFormWithQuestions() {
        this.questionList.add(new Question("1. What symptoms/problems do you experience" +
                "during a period of mental health crisis?", ""));
        this.questionList.add(new Question("2. What medications are helpful/not helpful for you? " +
                "Try to give detail to assist medical professionals who " +
                "may be helping you in crisis."));
        this.questionList.add(new Question("A. I agree to administration of " +
                "the following medication(s):", ""));
        this.questionList.add(new Question("B. I do not agree to administration of the " +
                "following medication(s):", ""));
        this.questionList.add(new Question("C. Other information about medications " +
                "(allergies, side effects)", ""));
        this.questionList.add(new Question("3. What are your preferences regarding treatment " +
                "facilities?"));
        this.questionList.add(new Question("A. I agree to admission to the following " +
                "hospital(s):\n" + "Note: Admission to a specific facility may be" +
                "limited because of lack of an available bed. ", ""));
        this.questionList.add(new Question("B. I do not agree to admission to the" +
                "following hospital(s):", ""));
        this.questionList.add(new Question("C. Other information about" +
                "hospitalization:", ""));
        this.questionList.add(new Question("5. Crisis Precipitants. The following may" +
                "cause me to experience a mental health crisis:", ""));
        this.questionList.add(new Question("6. Protective Factors. The following" +
                "may help me avoid a mental health crisis:", ""));
        this.questionList.add(new Question("7. Response to Hospital. I usually respond" +
                "to the hospital as follows:", ""));
        this.questionList.add(new Question("8. Preferences for Staff Interactions.\n" +
                "Staff of the hospital or crisis unit can help me by doing the following:",
                ""));
        this.questionList.add(new Question("9. I give permission for the following" +
                " people" +
                "to visit me in the hospital: ", ""));
        this.questionList.add(new Question("10. The following are my" +
                "preferences about ECT and other treatments"));
        this.questionList.add(new Question("A. Do I consent to ECT?", ""));
        this.questionList.add(new Question("B. My preferences about other" +
                "treatments:", ""));
        this.questionList.add(new Question("11. Other Instructions"));
        this.questionList.add(new Question("A. If I am hospitalized," +
                "I want the following to be taken care of at my home: ", ""));
        this.questionList.add(new Question("b. I understand that the information " +
                "in this document may be shared by my mental health treatment " +
                "provider with any other mental health treatment " +
                "provider who may serve me when necessary to " +
                "30\n" +
                "provide treatment in accordance with this advance instruction. " +
                "Other instructions about sharing of " +
                "information are as follows:", ""));
    }

    public int numQuestions() {
        return this.questionList.size();
    }

    public Question getQuestion(int idx) {
        return this.questionList.get(idx);
    }
}
