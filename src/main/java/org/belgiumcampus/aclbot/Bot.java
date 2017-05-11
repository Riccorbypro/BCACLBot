package org.belgiumcampus.aclbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.belgiumcampus.aclbot.commands.HelpCommand;
import org.belgiumcampus.aclbot.commands.Register;
import org.belgiumcampus.aclbot.commands.Start;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingCommandBot {

    Connect conn = new Connect();

    public Bot() {
        register(new Register(conn));
        register(new Start());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
        registerDefaultAction((absSender, message) -> {
            if (processFile(message) != null) {
                SendMessage fileReceived = new SendMessage();
                fileReceived.setChatId(message.getChatId().toString());
                fileReceived.setText("File has been received and is being processed. Thank you for your submission.");
                try {
                    absSender.sendMessage(fileReceived);
                } catch (TelegramApiException ex) {
                    System.err.println(ex);
                }
            } else {
                SendMessage commandUnknownMessage = new SendMessage();
                commandUnknownMessage.setChatId(message.getChatId());
                commandUnknownMessage.setText("The command '" + message.getText() + "' is not recognised. Please see the following help message.");
                try {
                    absSender.sendMessage(commandUnknownMessage);
                } catch (TelegramApiException e) {
                    System.err.println(e);
                }
                helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
            }
        });
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_USERNAME;
    }

    public SendMessage processFile(Message message) {
        if (message.hasDocument()) {
            Cluster c = getClusterFromUser(message.getFrom());
            if (c != null) {
                try {
                    Document document = message.getDocument();
                    String fileID = document.getFileId();
                    GetFile getfile = new GetFile();
                    getfile.setFileId(fileID);
                    File file = getFile(getfile);
                    insertFileUpload(message.getFrom(), document);
                    Runnable dropbox = new Dropbox(file.getFileUrl(Config.BOT_TOKEN), document.getFileName(), null);
                    Thread thread = new Thread(dropbox);
                    thread.start();
                    SendMessage sm = new SendMessage();
                    sm.setChatId(message.getChatId().toString());
                    sm.setText("File received. Thank you for your submission.");
                    return sm;
                } catch (TelegramApiException ex) {
                    System.err.println(ex);
                }
            } else {
                SendMessage sm = new SendMessage();
                sm.setChatId(message.getChatId().toString());
                sm.setText("You are not part of a cluster. Please /register.\n\nIf you have registered, please contact @Riccorbypro or @R34P3R for support.");
                return sm;
            }
        }
        return null;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }

    private Cluster getClusterFromUser(User from) {
        try {
            ResultSet rs = conn.query("SELECT * FROM students WHERE `telegramID`=?", from.getId());
            int clusterID = -1;
            while (rs.next()) {
                clusterID = rs.getInt("clusterID");
            }
            if (clusterID > 0) {
                rs = conn.query("SELECT * FROM clusters WHERE `clusterID`=?", clusterID);
                Cluster c = null;
                while (rs.next()) {
                    c = new Cluster(rs.getInt("clusterID"), rs.getString("clusterName"), rs.getString("dropboxLink"));
                }
                return c;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    private void insertFileUpload(User from, Document document) {
        conn.insert("INSERT INTO uploads(`telegramID`, `time`, `fileName`) VALUES(?, ?, ?)", from.getId(), new Date(), document.getFileName());
    }

}
