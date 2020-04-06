package edu.upenn.cis350.advanceddirectives.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class BasicDatabase extends Database {
    private HashMap<String, Person> people;

    public BasicDatabase() {
        this.people = new HashMap<String, Person>();
    }

    @Override
    public Person getPerson(String username) {
        return this.people.get(username);
    }

    @Override
    public Person addPerson(Person person) {
        return this.people.put(person.getUsername(), person);
    }

    @Override
    public Person removePerson(String username) {
        return this.people.remove(username);
    }

    @Override
    public Collection<Person> getAllPeople() {
        return this.people.values();
    }

    @Override
    public Iterator<Person> getPeopleIterator() {
        return this.people.values().iterator();
    }

    @Override
    public boolean isAvailable(String username) {
        return !this.people.containsKey(username);
    }
}
