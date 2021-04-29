package model.prank;

import model.mail.Message;
import model.mail.Person;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {

    private final Person sender;
    private final List<Person> victims;
    private final List<Person> witnesses;
    private final String message;

    public Prank(Person sender, List<Person> victims, List<Person> witnesses, String message) {
        this.sender = sender;
        this.victims = victims;
        this.witnesses = witnesses;
        this.message = message;
    }

    /**
     * Generate a message to send
     * @return : Message
     */
    public Message generateMessage() {
        return new Message(sender.getEmail(),
                victims.stream().map(Person::getEmail).collect(Collectors.toList()).toArray(new String[]{}),
                witnesses.stream().map(Person::getEmail).collect(Collectors.toList()).toArray(new String[]{}),
                this.message);
    }
}
