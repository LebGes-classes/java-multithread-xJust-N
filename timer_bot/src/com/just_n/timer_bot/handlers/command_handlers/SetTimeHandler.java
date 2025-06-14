package com.just_n.timer_bot.handlers.command_handlers;

import com.just_n.timer_bot.handlers.Handler;
import com.just_n.timer_bot.handlers.MessageService;

public class SetTimeHandler implements Handler {
    @Override
    public void handle(MessageService messageService, String command) {
        String[] parts = command.split(" ", 3);
        if (parts.length < 2 || parts.length > 3) {
            messageService.send(
                    "Неверная команда\n" +
                    "Использование: /set [секунды] [сообщение]");
        }

        if (!parts[1].matches("^[1-9]\\d*$")) {
            messageService.send(
                    "Неверная команда\n" +
                    "Использование: /set [секунды] [сообщение]");
            return;
        }

        int sec = Integer.parseInt(parts[1]);
        Thread timeThread = new Thread(() -> {
            try {
                messageService.send("Таймер на " + sec + " секунд");
                Thread.sleep(sec * 1000L);
                messageService.send("Время (" + sec + " с.) вышло!");
                if(parts.length == 3)
                    messageService.send(parts[2]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        timeThread.start();
    }
}
