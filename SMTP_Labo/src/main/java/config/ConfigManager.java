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

    public ConfigManager() {
        loadProperties("./config/config.properties");
        loadVictimsFile("./config/victims.utf8");
        loadMessagesFile("./config/messages.utf8");
    }

    /**
     * Load and set properties defined in .config/config.properties
     * @param propertiesPath : path to config.properties file
     */
    private void loadProperties(String propertiesPath) throws RuntimeException {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(propertiesPath);
            Properties prop = new Properties();
            prop.load(inputStream);

            // server address
            smtpServerAddress = prop.getProperty("smtpServerAddress");
            if (smtpServerAddress.isEmpty()) {
                throw new RuntimeException("Smtp server address must be provided. Please configure the config.properties file");
            }
            // server port
            try {
                smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Smtp server port must be provided and be a number. Please configure the config.properties file");
            }
            // number of groups
            try {
                numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Number of groups must be provided and be a number. Please configure the config.properties file");
            }
            if (numberOfGroups < 3) {
                throw new RuntimeException("Number of groups must be at least 3");
            }
            // witnesses (can be empty)
            String[] witnesses = prop.getProperty("witnessesToCC").split(",");
            witnessesToCC = new ArrayList<>();
            for (String witness : witnesses) {
                witnessesToCC.add(new Person(witness));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and set victims defined in ./config/victims.utf8
     * @param victimsPath : path to victims.utf8 file
     */
    private void loadVictimsFile(String victimsPath) {
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(victimsPath), StandardCharsets.UTF_8));
            String line = bReader.readLine();
            while (line != null) {
                victims.add(new Person(line));
                line = bReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load and set messages defined in ./config/messages.utf8
     * @param messagesPath : path to messages.utf8 file
     */
    private void loadMessagesFile(String messagesPath) {
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(messagesPath), StandardCharsets.UTF_8));
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter Smtp Server Address
     * @return : server address
     */
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    /**
     * Getter Smtp Server port
     * @return : server port
     */
    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    /**
     * Getter number of groups
     * @return : number of groups
     */
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    /**
     * Getter victims
     * @return : list of victims
     */
    public List<Person> getVictims() {
        return new ArrayList<>(victims);
    }

    /**
     * Getter witnesses
     * @return : list of witnesses
     */
    public List<Person> getWitnessesToCC() {
        return new ArrayList<>(witnessesToCC);
    }

    /**
     * Getter messages
     * @return : list of prank messages
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

}
