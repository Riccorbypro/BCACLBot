package org.belgiumcampus.aclbot.commands;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Start extends BotCommand {

    public Start() {
        super("start", "Start the bot");
    }

    @Override
    public void execute(AbsSender as, User user, Chat chat, String[] strings) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setText("Bot started: Please /register");
            as.sendMessage(message);
        } catch (TelegramApiException ex) {
            System.err.println(ex);
        }
    }

}
