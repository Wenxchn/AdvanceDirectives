package edu.upenn.cis350.advanceddirectives.data;

import java.util.HashMap;
import java.util.Map;

public class MoodCalendar {

    private HashMap<String, String> map; //Date-Mood eg. "4/1/2020" maps to "Mood: 4"

    public MoodCalendar() {
        map = new HashMap<>();
    }

    public MoodCalendar(String s) {
        if (s.length() % 33 != 0) {
            throw new RuntimeException();
        }
        int num = s.length() / 33;
        map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            String date = s.substring(i * 33, i * 33 + 26);
            String mood = s.substring(i * 33 + 26, i * 33 + 26 + 7);
            map.put(date, mood);
        }
    }

    public String getMood(String date) {
        return map.get(date);
    }

    public void setMood(String date, String mood) {
        map.put(date, mood);
    }

    public String toString() {
        // Length = 26
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey() + entry.getValue());
        }
        return stringBuilder.toString();
    }



}
