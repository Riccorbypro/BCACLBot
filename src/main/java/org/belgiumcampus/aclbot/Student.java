package org.belgiumcampus.aclbot;

public class Student {

    private long telegramID;
    private long chatID;
    private String firstName, surname, username;
    private Group group;

    public Student(long telegramID, long chatID, String firstName, String surname, String username, Group group) {
        this.telegramID = telegramID;
        this.chatID = chatID;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.group = group;
    }

    public long getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(long telegramID) {
        this.telegramID = telegramID;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
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

    public String getUsername() {
        if (username != "null") {
            return "@" + username;
        } else {
            return "No Username";
        }
    }

    public String getUsernameNoAt() {
        if (username != "null") {
            return username;
        } else {
            return "No Username";
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
