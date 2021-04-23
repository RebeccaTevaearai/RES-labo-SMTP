package smtp;

import model.mail.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.net.Socket;

/**
 *
 * @Auteurs Jesus Christ
 */

public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpAddress;//wesh
    private int smtpPort = 420;


    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(String smtpAddress, int port) {
        this.smtpPort = port;
        this.smtpAddress = smtpAddress;
    }

    @Override
    public void sendMessage(Message message) throws IOException {

        Socket socket = new Socket(smtpAddress, smtpPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        String line = reader.readLine();
        LOG.info(line);
        writer.printf("EHLO localhost\n");
        LOG.info(line);

        for (int i = 0; i <= 6; i++) {
            line = reader.readLine();
            LOG.info(line);
            if (!(line.startsWith("250-"))) {
                throw new IOException("error");
            }
        }

        if (!(line.startsWith("250 "))) {
            throw new IOException("error");
        }

        LOG.info(line);

       writer.write("MAIL FROM: " + message.getFrom() + "\n");
       writer.flush();
       line = reader.readLine();
       LOG.info(line);

       writer.write("RCPT TO: " + message.getTo() + "\n");
       writer.flush();
       line = reader.readLine();
       LOG.info(line);

       for (String s : message.getCc()) {
           writer.write("RCPT TO: " + s + "\n");
           writer.flush();
       }

       writer.write("DATA" + "\n");
       writer.flush();
       writer.write(message.getMessage()); //non il faudra write from sender + to victims avant d'envoyer le message
       writer.flush();







    }

}
