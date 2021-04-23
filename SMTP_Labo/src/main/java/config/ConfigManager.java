package config;

import model.mail.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    private String smtpServerAddress;
    private int smtpServerPort;
    private int numberOfGroups;
    private List<Person> witnessesToCC;
    private final List<String> messages = new ArrayList<>();
    private final List<Person> victims = new ArrayList<>();

    public ConfigManager() throws IOException {
        loadProperties();
        loadVictimsFile("./config/victims.utf8");
        loadMessagesFile("./config/messages.utf8");
    }

    public void loadProperties() {
        try (InputStream input = new FileInputStream("./config/config.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // set properties
            smtpServerAddress = prop.getProperty("smtpServerAddress");
            smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
            numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
            String[] witnesses = prop.getProperty("witnessesToCC").split(",");
            witnessesToCC = new ArrayList<>();
            for (String witness : witnesses) {
                witnessesToCC.add(new Person(witness));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadVictimsFile(String filename) throws IOException {
        try (BufferedReader bReader = new BufferedReader(
                                      new InputStreamReader(
                                      new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line = bReader.readLine();
            while (line != null) {
                victims.add(new Person(line));
                line = bReader.readLine();
            }
        }
    }

    public void loadMessagesFile(String filename) throws IOException {
        try (BufferedReader bReader = new BufferedReader(
                                      new InputStreamReader(
                                      new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line = bReader.readLine();
            StringBuilder message = new StringBuilder();
            while (line != null) {
                if (line.equals("***")) {
                    messages.add(message.toString());
                    message = new StringBuilder();
                } else {
                    message.append(line).append("\n");
                }
                line = bReader.readLine();
            }
        }
    }

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public List<Person> getVictims() {
        return new ArrayList<>(victims);
    }

    public List<Person> getWitnessesToCC() {
        return new ArrayList<>(witnessesToCC);
    }

    public List<String> getMessages() {
        return messages;
    }


}
