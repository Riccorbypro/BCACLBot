package org.belgiumcampus.aclbot;

public class Student {

    private int telegramID;
    private long chatID;
    private String firstName, surname;
    private Group group;

    public Student(int telegramID, long chatID, String firstName, String surname, Group group) {
        this.telegramID = telegramID;
        this.chatID = chatID;
        this.firstName = firstName;
        this.surname = surname;
        this.group = group;
    }

    public int getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(int telegramID) {
        this.telegramID = telegramID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

}
