package model.mail;

public class Message {

    private String from;
    private String[] to = new String[0];
    private String[] cc = new String[0];
    private String data;

    public String getFrom() {
        return from;
    }

    public void setFrom() {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getMessage() {
        return data;
    }

    public void setMessage(String subject) {
        this.data = subject;
    }

}
