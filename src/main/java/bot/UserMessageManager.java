package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import workWithDB.MainDBWork;

import java.sql.SQLException;

public class UserMessageManager {

    public static String[] keyButtons = {"Send message", "Take message"};

    public static int userMessageUpdate(Update update, TelegramBot telegramBot) throws SQLException, InterruptedException {

        if (update.message().text().equals("Let's go")) {

            SendMessage message = new SendMessage(update.message().from().id(), "What do you want?");

            message.replyMarkup(KeyboardBot.defKeyboard(keyButtons));

            telegramBot.execute(message);

            return 0;

        }

        if (update.message().text().equals("Send message")) {

            SendMessage message = new SendMessage(update.message().from().id(), "Write your message");

            telegramBot.execute(message);

            return 1;

        }

        if (update.message().text().equals("Take message")) {
            SendMessage message = new SendMessage(update.message().from().id(), "Write your code");

            telegramBot.execute(message);

            return 2;

        }
        return -1;
    }

    public static boolean firstMessage(Long userId, Update update, TelegramBot telegramBot) throws SQLException {

        if (!MainDBWork.hasUserId(userId)) {
            MainDBWork.saveUserData(userId, update.message().from().username());

            SendMessage sendMessage = new SendMessage(userId, "Hello!\n\n\nLet's start");

            telegramBot.execute(sendMessage);

            if (update.message().text().equals("Let's go")) {

                SendMessage message = new SendMessage(userId, "What do you want?");

                message.replyMarkup(KeyboardBot.defKeyboard(keyButtons));

                telegramBot.execute(message);

            }

            return true;
        }
        return false;
    }
}
