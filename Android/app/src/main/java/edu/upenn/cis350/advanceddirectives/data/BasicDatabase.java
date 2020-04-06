package edu.upenn.cis350.advanceddirectives.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class BasicDatabase extends Database {
    private int CurrID = 0;
    private HashMap<Integer, Person> people;

    public BasicDatabase() {
        this.people = new HashMap<Integer, Person>();
    }

    @Override
    public int getAndIncrementCurrID() {
        return CurrID++;
    }

    @Override
    public Person getPerson(int ID) {
        return this.people.get(ID);
    }

    @Override
    public Person addPerson(Person person) {
        return this.people.put(person.getID(), person);
    }

    @Override
    public Person removePersonByID(int ID) {
        return this.people.remove(ID);
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
        for (Person person: this.people.values()) {
            if (person.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
