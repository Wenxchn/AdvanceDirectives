package edu.upenn.cis350.advanceddirectives.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class MyMongoDatabase extends Database {

    WebStuff web;

    private class WebStuff extends AsyncTask<Object, Void, Object> {

        private Person getPersonFromJSON(JSONObject dbPerson) {
            if (dbPerson.length() == 0) { return null; }
            try {
                String username = (String) dbPerson.get("username");
                String password = (String) dbPerson.get("password");
                String firstName = (String) dbPerson.get("firstName");
                String lastName = (String) dbPerson.get("lastName");
                String email = (String) dbPerson.get("email");
                String phone = (String) dbPerson.get("phone");
                String address = (String) dbPerson.get("address");
                String birthday = (String) dbPerson.get("birthday");
                Person person = new Person(username, password,
                        firstName, lastName, email, phone, address, birthday);
                person.setForm((String) dbPerson.get("form"));
                person.setMoodCalendar((String) dbPerson.get("moodCalendar"));
                return person;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private JSONObject getJSONFromPerson(Person person) {
            JSONObject dbPerson = new JSONObject();
            try {
                dbPerson.put("username", person.getUsername());
                dbPerson.put("password", person.getPassword());
                dbPerson.put("firstName", person.getFirstName());
                dbPerson.put("lastName", person.getLastName());
                dbPerson.put("email", person.getEmail());
                dbPerson.put("phone", person.getPhone());
                dbPerson.put("address", person.getAddress());
                dbPerson.put("birthday", person.getBirthday());
                dbPerson.put("moodCalendar", person.getMoodCalendar().toString());
                dbPerson.put("form", person.getForm().getAnswers());
                return dbPerson;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private Object getWebStuffs(String url) {
            try {
                URL u = new URL(url);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.connect();
                Scanner in = new Scanner(u.openStream());
                StringBuilder response = new StringBuilder();
                while (in.hasNext()) {
                    response.append(in.nextLine());
                }
                in.close();
                c.disconnect();
                return new JSONObject(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            String action = (String) objects[0];
            String urlStart = "http://10.0.2.2:5500/";
            try {
                switch (action) {
                    case "get": {
                        String url = urlStart + "get" + "?username=" + objects[1];
                        return getPersonFromJSON((JSONObject) getWebStuffs(url));
                    }
                    case "set": {
                        Person person = (Person) objects[1];
                        String str = getJSONFromPerson(person).toString();
                        if (true) {
                            throw new RuntimeException(str);
                        }
                        String encoded = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
                        System.out.println(encoded);
                        String url = urlStart + "set" + "?person=" + encoded;
                        return getPersonFromJSON((JSONObject) getWebStuffs(url));
                    }
                    case "remove": {
                        String url = urlStart + "remove" + "?username=" + objects[1];
                        return getPersonFromJSON((JSONObject) getWebStuffs(url));
                    }
                    case "all": {
                        Collection<Person> people = new LinkedList<>();
                        String url = urlStart + "all";
                        JSONArray arr = (JSONArray) getWebStuffs(url);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject dbPerson = (JSONObject) arr.get(i);
                            people.add(getPersonFromJSON(dbPerson));
                        }
                        return people;
                    }
                    case "available": {
                        String url = urlStart + "remove" + "?username=" + objects[1];
                        return getWebStuffs(url);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public MyMongoDatabase() {
        web = new WebStuff();
    }

    @Override
    public Person getPerson(String username) {
        Person person = null;
        try {
            web.execute("get", username);
            person = (Person) web.get();
            web = new WebStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Person addPerson(Person person) {
        Person previous = null;
        try {
            web.execute("set", person);
            previous = (Person) web.get();
            web = new WebStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previous;
    }

    @Override
    public Person removePerson(String username) {
        Person previous = null;
        try {
            web.execute("remove", username);
            previous = (Person) web.get();
            web = new WebStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previous;
    }

    @Override
    public Collection<Person> getAllPeople() {
        Collection<Person> people = null;
        try {
            web.execute("all");
            people = (Collection<Person>) web.get();
            web = new WebStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public boolean isAvailable(String username) {
        boolean available = false;
        try {
            web.execute("available", username);
            available = (Boolean) web.get();
            web = new WebStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return available;
    }
}
