package main;

import bot.MainBot;
import com.pengrad.telegrambot.TelegramBot;
import util.PropertiesUtil;

public class Start {

    public static final TelegramBot telegramBot = new TelegramBot(PropertiesUtil.get(MainBot.BOTTOKEN));

    public static void main(String[] args) {
        telegramBot.setUpdatesListener(new MainBot());
    }
}

