package com.just_n.timer_bot.handlers.command_handlers;

import com.just_n.timer_bot.handlers.Handler;
import com.just_n.timer_bot.handlers.MessageService;

public class StartHandler implements Handler {
    @Override
    public void handle(MessageService messageService, String command) {
        messageService.send(
                "Привет! Я таймер бот\n" +
                "Используй /set [секунды] [сообщение] чтобы установить таймер.");
    }
}
