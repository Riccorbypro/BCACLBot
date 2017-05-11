package org.belgiumcampus.aclbot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = createTelegramBotsApi();
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException ex) {
            System.err.println(ex);
        }
    }

    private static TelegramBotsApi createTelegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi;

        telegramBotsApi = createLongPollingTelegramBotsApi();

        return telegramBotsApi;
    }

    private static TelegramBotsApi createLongPollingTelegramBotsApi() {
        return new TelegramBotsApi();
    }
}
