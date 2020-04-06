package edu.upenn.cis350.advanceddirectives.data;

import java.util.Collection;
import java.util.Iterator;

public abstract class Database {
    public abstract int getAndIncrementCurrID();
    public abstract Person getPerson(int ID);
    public abstract Person addPerson(Person person);
    public abstract Person removePersonByID(int ID);
    public abstract Collection<Person> getAllPeople();
    public Iterator<Person> getPeopleIterator() {
        return getAllPeople().iterator();
    }
    public abstract boolean isAvailable(String username);
}
