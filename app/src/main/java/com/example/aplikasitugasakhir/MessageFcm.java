package com.example.aplikasitugasakhir;

public class MessageFcm {
    private String to;
    private Notification notification;

    public MessageFcm() {
    }

    public MessageFcm(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
