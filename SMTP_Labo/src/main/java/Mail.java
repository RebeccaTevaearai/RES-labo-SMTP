import config.ConfigManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.SmtpClient;
import java.io.*;
import static java.lang.System.exit;

public class Mail {

    public static void main(String[] args) {
        ConfigManager configManager = null;
        try {
           configManager = new ConfigManager();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            exit(1);
        }
        PrankGenerator prankGenerator = new PrankGenerator(configManager);
        SmtpClient smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
        try {
            for (Prank prank : prankGenerator.generatePranks()) {
                smtpClient.sendMessage(prank.generateMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
