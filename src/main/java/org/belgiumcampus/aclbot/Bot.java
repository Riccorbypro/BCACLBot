package org.belgiumcampus.aclbot;

import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class Bot extends org.telegram.telegrambots.bots.TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText() && !message.isCommand()) {
                    String text = message.getText();

                } else if (message.hasText() && message.isCommand()) {

                } else if (message.hasDocument()) {
                    Document document = message.getDocument();
                    String fileID = document.getFileId();
                    GetFile getfile = new GetFile();
                    getfile.setFileId(fileID);
                    File file = getFile(getfile);
                    Runnable dropbox = new Dropbox(file.getFilePath());
                    Thread thread = new Thread(dropbox);
                    thread.start();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_USERNAME;
    }

}
