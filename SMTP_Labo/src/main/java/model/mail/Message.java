package model.mail;

public class Message {

    private final String from;
    private final String[] to;
    private final String[] cc;
    private final String data;

    public Message(String from, String[] to, String[] cc, String data) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.data = data;
    }

    /**
     * Getter from
     * @return : message sender
     */
    public String getFrom() {
        return from;
    }

    /**
     * Getter to
     * @return : message recipient(s)
     */
    public String[] getTo() {
        return to;
    }

    /**
     * Getter cc
     * @return : message recipient(s) to cc
     */
    public String[] getCc() {
        return cc;
    }

    /**
     * Get data
     * @return : message corps
     */
    public String getData() {
        return data;
    }

}
