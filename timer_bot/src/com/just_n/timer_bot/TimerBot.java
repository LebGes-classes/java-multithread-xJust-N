package com.just_n.timer_bot;

import com.just_n.timer_bot.handlers.Handler;
import com.just_n.timer_bot.handlers.HandlerFactory;
import com.just_n.timer_bot.handlers.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TimerBot extends TelegramLongPollingBot {
    private final String botUsername;
    private final String botToken;

    public TimerBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }


    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    private void handleCommand(long chatId, String command) {
        Handler handler = HandlerFactory.getHandler(command);
        if (handler == null) {
            sendMessage(chatId, "Невозможно обработать команду");
            return;
        }
        handler.handle(new MessageService(this, chatId), command);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            handleCommand(chatId, update.getMessage().getText());
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
    }
}