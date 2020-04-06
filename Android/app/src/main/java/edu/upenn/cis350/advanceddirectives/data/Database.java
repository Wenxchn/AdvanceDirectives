package edu.upenn.cis350.advanceddirectives.data;

import java.util.Collection;
import java.util.Iterator;

public abstract class Database {
    public abstract Person getPerson(String username);
    public abstract Person addPerson(Person person);
    public abstract Person removePerson(String username);
    public abstract Collection<Person> getAllPeople();
    public Iterator<Person> getPeopleIterator() {
        return getAllPeople().iterator();
    }
    public abstract boolean isAvailable(String username);
}
