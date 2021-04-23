package model.prank;

import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {

    private Person sender;
    private final List<Person> victims = new ArrayList<>();
    private final List<Person> witnesses = new ArrayList<>();
    private String message;

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public void addVictims(List<Person> victims) {
        this.victims.addAll(victims);
    }

    public List<Person> getWitnesses() {
        return witnesses;
    }

    public void addWitnesses(List<Person> witnesses) {
        this.witnesses.addAll(witnesses);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
