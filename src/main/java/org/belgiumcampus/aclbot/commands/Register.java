package org.belgiumcampus.aclbot.commands;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Register extends BotCommand {

    public Register() {
        super("register", "Register with Belgium Campus for ACL");
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
            
            try{
                as.sendMessage(answer);
            } catch (TelegramApiException ex) {
                System.err.println(ex);
            }
        }
    }

}
