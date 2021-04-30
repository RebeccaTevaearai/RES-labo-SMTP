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

    /**
     * Generate a list of Prank
     * @return : list of pranks
     */
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<String> messages = configManager.getMessages();
        Collections.shuffle(messages);
        int nbGroups = configManager.getNumberOfGroups();
        int nbVictims = configManager.getVictims().size();
        if (nbVictims / nbGroups < 3) {
            nbGroups = nbVictims / 3;
            System.err.printf("Not enough victims to generate enough groups. Configure your number of groups with %d groups", nbGroups);
        }

        // create a prank for each group
        List<Group> groups = generateGroups(configManager.getVictims(), nbGroups);
        List<Person> witnesses = configManager.getWitnessesToCC();
        int index = 0;
        for (Group group : groups) {
            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            String message = messages.get(index);
            index = (index + 1) % messages.size();
            pranks.add(new Prank(victims.remove(0), victims, witnesses, message));
        }
        return pranks;
    }

    /**
     * Generate a list of Group based on a list of victims and a number of groups
     * @param victims : list of victims
     * @param nbGroups : number of groups
     * @return list of groups
     */
    private List<Group> generateGroups(List<Person> victims, int nbGroups) {
        List<Person> availableVictims = new ArrayList<>(victims);
        Collections.shuffle(availableVictims);
        List<Group> groups = new ArrayList<>();
        // create groups
        for (int i = 0; i < nbGroups; i++) {
            Group group = new Group();
            groups.add(group);
        }
        // add victims to created groups
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
