package org.belgiumcampus.aclbot.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.belgiumcampus.aclbot.Cluster;
import org.belgiumcampus.aclbot.Connect;
import org.belgiumcampus.aclbot.Group;
import org.belgiumcampus.aclbot.Student;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Register extends BotCommand {

    private Connect conn;

    public Register(Connect conn) {
        super("register", "Register with Belgium Campus for ACL");
        this.conn = conn;
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] arguments) {
        if (arguments.length < 5) {
            String message = "Please register as shown below:\n\n"
                    + "/register <name> <surname>, Group <number> <session> \n\n"
                    + "Eg.\n\n"
                    + "/register Peter Pumpkin, Group 13 AM";
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText(message);

            try {
                as.sendMessage(answer);
            } catch (TelegramApiException ex) {
                System.err.println(ex);
            }
        } else if (checkUser(user)) {
            String message = "You are already registered.";
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText(message);
            try {
                as.sendMessage(answer);
            } catch (TelegramApiException ex) {
                System.err.println(ex);
            }
        } else {
            String name = "";
            int pos = 0;
            for (String argument : arguments) {
                if (argument.contains(",")) {
                    name += argument.split(",")[0];
                    break;
                } else {
                    name += argument + " ";
                    pos++;
                }
            }
            String[] names = name.split(" ");
            String firstName = names[0];
            String surname = "";
            for (int i = 1; i < names.length; i++) {
                surname += names[i] + " ";
            }

            int group = 0;
            String timeslot = "";
            pos += 2;
            try {
                group = Integer.parseInt(arguments[pos]);
                pos++;
                if (arguments[pos].toUpperCase().equals("AM") || arguments[pos].toUpperCase().equals("PM")) {
                    timeslot = arguments[pos].toUpperCase();
                    Group groupObj = null;
                    if ((groupObj = checkGroup(group, timeslot)) != null) {
                        Student student = new Student(user.getId(), chat.getId(), firstName, surname, user.getUserName(), groupObj);
                        if (!registerStudent(student)) {
                            String message = "You have been registered as:\n"
                                    + student.getFirstName() + " " + student.getSurname() + ".\nYour username is: "
                                    + student.getUsername() + "\nYou are in ACL Group "
                                    + student.getGroup().getGroupNo() + " " + student.getGroup().getTimeslot()
                                    + "\n\nYou are part of Cluster " + student.getGroup().getCluster().getClusterName();

                            if (student.getGroup().getMembers().size() > 0) {
                                message += "\n\nYou will be working with:\n";;
                                for (Student member : student.getGroup().getMembers()) {
                                    message += "\n";
                                    message += member.getFirstName() + " " + member.getSurname() + " (" + member.getUsername() + ")";
                                }
                            }

                            notifyOtherGroupMembers(student, student.getGroup().getMembers(), as);

                            SendMessage answer = new SendMessage();
                            answer.setChatId(chat.getId().toString());
                            answer.setText(message);
                            System.out.println("New Registration: " + student.getFirstName() + " " + student.getSurname() + " (" + student.getUsername() + ")");
                            try {
                                as.sendMessage(answer);
                            } catch (TelegramApiException ex) {
                                System.err.println(ex);
                            }
                        }
                    }
                } else {
                    String message = "Registration Failed! Please check that your group and timeslot are correct.";
                    SendMessage answer = new SendMessage();
                    answer.setChatId(chat.getId().toString());
                    answer.setText(message);
                    try {
                        as.sendMessage(answer);
                    } catch (TelegramApiException ex) {
                        System.err.println(ex);
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex);
                String message = "Registration Failed! Please check that your group and timeslot are correct.";
                SendMessage answer = new SendMessage();
                answer.setChatId(chat.getId().toString());
                answer.setText(message);
                try {
                    as.sendMessage(answer);
                } catch (TelegramApiException ex1) {
                    System.err.println(ex);
                }
            }
        }
    }

    public boolean checkUser(User user) {
        try {
            ResultSet rs = conn.query("SELECT * FROM students WHERE `telegramID`=?", user.getId());
            boolean isregistered = false;
            while (rs.next()) {
                isregistered = true;
            }
            return isregistered;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return true;
    }

    private Group checkGroup(int group, String timeslot) throws SQLException {
        ResultSet rs = conn.query("SELECT * FROM `groups` WHERE `groupName`=?", group + " " + timeslot);
        Group g = null;
        while (rs.next()) {
            g = new Group(rs.getInt("groupID"), group, timeslot, new ArrayList<Student>(), getCluster(rs.getInt("clusterID")));
        }
        rs = conn.query("SELECT * FROM `students` WHERE `groupID`=?", g.getGroupID());
        while (rs.next()) {
            g.addMember(new Student(rs.getInt("telegramID"), rs.getLong("chatID"), rs.getString("firstName"), rs.getString("surname"), rs.getString("username"), g));
        }
        return g;
    }

    private Cluster getCluster(int clusterID) throws SQLException {
        ResultSet rs = conn.query("SELECT * FROM `clusters` WHERE `clusterID`=?", clusterID);
        Cluster c = null;
        while (rs.next()) {
            c = new Cluster(rs.getInt("clusterID"), rs.getString("clusterName"));
        }
        return c;
    }

    private boolean registerStudent(Student student) {
        boolean result = conn.insert("INSERT INTO `students`(`telegramID`, `chatID`, `firstName`, `surname`, `username`, `groupID`, `leader`) VALUES (?, ?, ?, ?, ?, ?, ?);",
                student.getTelegramID(), student.getChatID(), student.getFirstName(), student.getSurname(), student.getUsernameNoAt(), student.getGroup().getGroupID(), false);
        return result;
    }

    private void notifyOtherGroupMembers(Student student, ArrayList<Student> members, AbsSender as) {
        for (Student member : members) {
            SendMessage message = new SendMessage();
            message.setChatId(member.getChatID());
            message.setText(student.getFirstName() + " " + student.getSurname() + " (" + student.getUsername() + ") just joined your group.");
            try {
                as.sendMessage(message);
            } catch (TelegramApiException ex) {
                System.err.println(ex);
            }
        }
    }

}
