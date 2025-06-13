package com.just_n.timer_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerBot extends TelegramLongPollingBot {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final String botUsername;
    private final String botToken;

    public TimerBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.startsWith("/set")) {
                handleSetCommand(chatId, messageText);
            } else if (messageText.equals("/start")) {
                sendMessage(chatId, "Используй /set [секунды] [сообщение] чтобы установить таймер.");
            }
        }
    }

    private void handleSetCommand(long chatId, String command) {
        String[] parts = command.split(" ", 3);
        if (parts.length < 2) {
            sendMessage(chatId, "Использование: /set [секунды] [сообщение]");
            return;
        }

        try {
            int seconds = Integer.parseInt(parts[1]);
            String message = (parts.length >= 3) ? parts[2] : "Время вышло!";
            scheduler.schedule(() -> {
                sendMessage(chatId, message);
            }, seconds, TimeUnit.SECONDS);

            sendMessage(chatId, "Таймер установлен на " + seconds + " секунд!");
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Некорректное число секунд");
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}