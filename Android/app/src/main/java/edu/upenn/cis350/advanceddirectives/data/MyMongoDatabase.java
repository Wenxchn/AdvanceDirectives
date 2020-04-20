package edu.upenn.cis350.advanceddirectives.data;

import android.os.AsyncTask;


import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyMongoDatabase extends Database {

    WebStuff web;

    private class WebStuff extends AsyncTask<Object, Void, Object> {
        ConnectionString s = new ConnectionString(
                "mongodb+srv://zqiu:12345@cluster0-bmaew.mongodb.net/" +
                        "CIS350?retryWrites=true&w=majority");

        MongoClient mongoClient = MongoClients.create(s);

//                "mongodb://[username:password@]host1[:port1][,host2[:port2]
//                ,...[,hostN[:portN]]][/[database][?options]]"

        MongoDatabase db = mongoClient.getDatabase("CIS350");
        MongoCollection<Document> collection = db.getCollection("persons");

        private Person getPersonFromDB(Document dbPerson) {
            if (dbPerson == null) { return null; }
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
            person.setForm((String[]) dbPerson.get("form"));
            person.setMoodCalendar((String) dbPerson.get("moodCalendar"));
            return person;
        }

        private Document getDocumentFromPerson(Person person) {
            Document dbPerson = new Document();
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
        }

        @Override
        protected Object doInBackground(Object... objects) {
            String action = (String) objects[0];
            switch (action) {
                case "get": {
                    Document dbPerson = collection.find(Filters.eq("username",
                            (String) objects[1])).first();
                    return getPersonFromDB(dbPerson);
                }
                case "set": {
                    Person person = (Person) objects[1];
                    Person previous = getPersonFromDB(collection.findOneAndDelete(
                            Filters.eq("username", person.getUsername())));
                    Document dbPerson = getDocumentFromPerson(person);
                    collection.insertOne(dbPerson);
                    return previous;
                }
                case "remove": {
                    String username = (String) objects[1];
                    return getPersonFromDB(collection.findOneAndDelete(
                            Filters.eq("username", username)));
                }
                case "all": {
                    Collection<Person> people = new LinkedList<>();
                    for (Document dbPerson: collection.find()) {
                        people.add(getPersonFromDB(dbPerson));
                    }
                    return people;
                }
                case "available": {
                    String username = (String) objects[1];
                    for (Document dbPerson: collection.find()) {
                        if (((String) dbPerson.get("username")).equals(username)) {
                            return false;
                        }
                    }
                    return true;
                }
                case "iterator": {
                    return new PersonIterator();
                }
            }
            return null;
        }
        private class PersonIterator implements Iterator<Person> {
            MongoCursor<Document> weirdIterator;

            public PersonIterator() {
                weirdIterator = collection.find().iterator();
            }

            @Override
            public boolean hasNext() {
                return weirdIterator.hasNext();
            }

            @Override
            public Person next() {
                if (hasNext()) {
                    return getPersonFromDB(weirdIterator.next());
                } else {
                    throw new NoSuchElementException();
                }
            }
        }
    }

    public MyMongoDatabase() {
        web = new WebStuff();
    }

    @Override
    public Person getPerson(String username) {
        Person person;
        try {
            web.execute("get", username);
            person = (Person) web.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return person;
    }

    @Override
    public Person addPerson(Person person) {
        Person previous;
        try {
            web.execute("set", person);
            previous = (Person) web.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return previous;
    }

    @Override
    public Person removePerson(String username) {
        Person previous;
        try {
            web.execute("remove", username);
            previous = (Person) web.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return previous;
    }

    @Override
    public Collection<Person> getAllPeople() {
        Collection<Person> people;
        try {
            web.execute("all");
            people = (Collection<Person>) web.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return people;
    }

    @Override
    public Iterator<Person> getPeopleIterator() {
        Iterator<Person> peopleIterator;
        try {
            web.execute("iterator");
            peopleIterator = (Iterator<Person>) web.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return peopleIterator;
    }

    @Override
    public boolean isAvailable(String username) {
        boolean available;
        try {
            web.execute("available", username);
            available = (Boolean) web.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return available;
    }
}
