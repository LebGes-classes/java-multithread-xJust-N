package com.just_n.timer_bot.handlers;

import com.just_n.timer_bot.TimerBot;

public class MessageService {
    private final TimerBot bot;
    private final long chatId;

    public MessageService(TimerBot bot, long chatId){
        this.bot = bot;
        this.chatId = chatId;
    }
    public void send(String text){
        bot.sendMessage(chatId, text);
    }
}
