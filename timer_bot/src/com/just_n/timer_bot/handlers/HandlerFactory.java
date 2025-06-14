package com.just_n.timer_bot.handlers;

import com.just_n.timer_bot.handlers.command_handlers.SetTimeHandler;
import com.just_n.timer_bot.handlers.command_handlers.StartHandler;

public class HandlerFactory {

    public static Handler getHandler(String command){
        if(command.startsWith("/start"))
            return new StartHandler();
        if(command.startsWith("/set"))
            return new SetTimeHandler();

        return null;
    }
}
