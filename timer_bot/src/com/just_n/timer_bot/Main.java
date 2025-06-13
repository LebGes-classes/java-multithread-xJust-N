package com.just_n.timer_bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        String botUsername = "";
        String botToken = "";
        TimerBot bot = new TimerBot(botUsername, botToken);
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Запуск бота");
            botsApi.registerBot(bot);
            Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


