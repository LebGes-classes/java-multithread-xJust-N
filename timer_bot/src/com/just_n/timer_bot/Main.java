package com.just_n.timer_bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        String botUsername = args[0];
        String botToken = args[1];
        TimerBot bot = new TimerBot(botUsername, botToken);
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            System.out.println("Невозможно подключиться к api\n" + e);
            return;
        }

        try {
            System.out.println("Запуск бота");
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            System.out.println("Непредвиденная ошибка:\n" + e);}
    }
}


