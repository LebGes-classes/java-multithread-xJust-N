package com.just_n.timer_bot;

import com.just_n.timer_bot.handlers.Handler;
import com.just_n.timer_bot.handlers.HandlerFactory;
import com.just_n.timer_bot.handlers.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TimerBot extends TelegramLongPollingBot implements MessageService {
    private final String botUsername;
    private final String botToken;
    private long chatId;

    public TimerBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public void send(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    private void handleCommand(String command) {
        Handler handler = HandlerFactory.getHandler(command);
        if (handler == null) {
            send("Невозможно обработать команду");
            return;
        }
        handler.handle(this, command);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            handleCommand(update.getMessage().getText());
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

}