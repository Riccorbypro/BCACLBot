package org.belgiumcampus.aclbot;

import org.belgiumcampus.aclbot.commands.Register;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingCommandBot {

    public Bot() {
        register(new Register());

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                System.err.println(e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
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

    @Override
    public void processNonCommandUpdate(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText() && !message.isCommand()) {
                    String text = message.getText();

                } else if (message.hasDocument()) {
                    Document document = message.getDocument();
                    String fileID = document.getFileId();
                    GetFile getfile = new GetFile();
                    getfile.setFileId(fileID);
                    File file = getFile(getfile);
                    Runnable dropbox = new Dropbox(file.getFileUrl(Config.BOT_TOKEN), document.getFileName(), null);
                    Thread thread = new Thread(dropbox);
                    thread.start();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
