package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private final List<Person> members = new ArrayList<>();

    /**
     * Add a Person to the group
     * @param person : member to add
     */
    public void addMember(Person person) {
        members.add(person);
    }

    /**
     * Getter members
     * @return : Members of the group
     */
    public List<Person> getMembers() {
        return new ArrayList<>(members);
    }
}
