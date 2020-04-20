package edu.upenn.cis350.advanceddirectives.data;

import java.util.HashMap;
import java.util.Map;

public class MoodCalendar {

    private HashMap<String, String> map; //Date-Mood eg. "4/1/2020" maps to "Mood: 4"

    public MoodCalendar() {
        map = new HashMap<>();
    }

    public String getMood(String date) {
        return map.get(date);
    }

    public void setMood(String date, String mood) {
        map.put(date, mood);
    }
}
