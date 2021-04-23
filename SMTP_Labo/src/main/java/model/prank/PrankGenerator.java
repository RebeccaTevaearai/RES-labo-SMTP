package model.prank;

import config.ConfigManager;
import model.mail.Group;
import model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrankGenerator {

    private final ConfigManager configManager;

    public PrankGenerator(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();

        List<String> messages = configManager.getMessages();
        Collections.shuffle(messages);
        int index = 0;

        int nbGroups = configManager.getNumberOfGroups();
        int nbVictims = configManager.getVictims().size();

        if (nbVictims / nbGroups < 3) {
            nbGroups = nbVictims / 3;
            System.err.printf("Not enough targets to generate enough groups. Continue using %d groups", nbGroups);
        }

        List<Group> groups = generateGroups(configManager.getVictims(), nbGroups);

        for (Group group : groups) {
            Prank prank = new Prank();
            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setSender(sender);
            prank.addVictims(victims);
            prank.addWitnesses(configManager.getWitnessesToCC());
            String message = messages.get(index);
            index = (index + 1) % messages.size();
            prank.setMessage(message);
            pranks.add(prank);
        }
        return pranks;
    }


    public List<Group> generateGroups(List<Person> victims, int nbGroups) {
        List<Person> availableVictims = new ArrayList<>(victims);
        Collections.shuffle(availableVictims);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < nbGroups; i++) {
            Group group = new Group();
            groups.add(group);
        }
        int turn = 0;
        Group victimGroup;
        while (availableVictims.size() > 0) {
            victimGroup = groups.get(turn);
            turn = (turn+1) % groups.size();
            Person victim = availableVictims.remove(0);
            victimGroup.addMember(victim);
        }
        return groups;
    }
}
