package smtp;

import model.mail.Message;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.net.Socket;

public class SmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private final String smtpAddress;
    private final int smtpServerPort;

    public SmtpClient(String address, int port) {
        this.smtpAddress = address;
        this.smtpServerPort = port;
    }

    /**
     * Send a message with SMTP
     * @param message : message to send
     */
    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(smtpAddress, smtpServerPort);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        // Starting session
        String line = reader.readLine();
        LOG.info(line);
        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        LOG.info(line);
        while (!line.startsWith("250 ")) {
            if (!line.startsWith("250")) {
                throw new IOException("SMTP error");
            }
            line = reader.readLine();
        }
        
        // Sender
        writer.write("MAIL FROM:<");
        writer.write(message.getFrom());
        writer.write(">\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error from sender");
        }
        
        // Recipient
        for (String to: message.getTo()) {
            commandRecipient(writer, reader, to);
        }
        for (String cc : message.getCc()) {
            commandRecipient(writer, reader, cc);
        }

        // Starting transmission
        writer.write("DATA\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("354")) {
            throw new IOException("SMTP error from recipient");
        }
        // from
        writer.write("From: " + message.getFrom() + "\r\n");
        // to
        writer.write("To: ");
        headerRecipients(writer, message.getTo());
        // cc
        writer.write("Cc: ");
        headerRecipients(writer, message.getCc());
        // data
        writer.write(message.getData());
        writer.write("\r\n");
        writer.write(".\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error transmitting message");
        }
        LOG.info("MESSAGE SEND");

        // Closing session
        writer.write("QUIT\r\n");
        writer.flush();
        socket.close();
    }

    /**
     * Settle the recipient (to or cc) of the message with the RCPT command
     * @param writer : writer
     * @param reader : reader
     * @param rec : recipient
     */
    private void commandRecipient(PrintWriter writer, BufferedReader reader, String rec) throws IOException {
        writer.write("RCPT TO:<");
        writer.write(rec);
        writer.write(">\r\n");
        writer.flush();
        String line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error recipient");
        }
    }

    /**
     * Settle the recipient (to or cc) of the message in the message header
     * @param writer : writer
     * @param rec : recipient
     */
    private void headerRecipients(PrintWriter writer, String[] rec) {
        for (int i = 0; i < rec.length; i++) {
            writer.write(rec[i]);
            if (i < rec.length - 1) {
                writer.write(",");
            }
        }
        writer.write("\r\n");
        writer.flush();
    }

}
