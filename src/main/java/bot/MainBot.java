package bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import main.Start;
import workWithDB.MainDBWork;

import java.sql.SQLException;
import java.util.List;

public class MainBot implements UpdatesListener {

    // test

    public static final String BOTTOKEN = "bot.token";
    private boolean isSave = false;
    private boolean isTake = false;

    @Override
    public int process(List<Update> list) {

        for (Update update : list) {

            Long updateId = update.message().from().id();
            String updateText = update.message().text();


            if (isSave) {
                try {
                    String code = MainDBWork.saveToDB(updateText);
                    SendMessage message = new SendMessage(updateId, "Your code - " + code);

                    Start.telegramBot.execute(message);

                    isSave = false;

                } catch (SQLException e) {
                }
            }
            if (isTake) {
                try {
                    String text = MainDBWork.takeFromDB(updateText);

                    SendMessage message = new SendMessage(updateId, "Your message: \n\n" + text);

                    Start.telegramBot.execute(message);

                    isTake = false;
                } catch (SQLException e) {
                }
            }

            if (!MainDBWork.hasUserId(updateId)) {

                try {
                    UserMessageManager.firstMessage(updateId, update, Start.telegramBot);
                } catch (SQLException e) {
                }
            }


            try {

                int flag = UserMessageManager.userMessageUpdate(update, Start.telegramBot);

                if (flag == 1) {
                    isSave = true;
                }
                if (flag == 2) {
                    isTake = true;
                }

            } catch (SQLException e) {
            } catch (InterruptedException e) {
            }

            if (!(updateText.equalsIgnoreCase("send message") || updateText.equalsIgnoreCase("take message"))) {

                SendMessage message = new SendMessage(updateId, "What do you want?");

                message.replyMarkup(KeyboardBot.defKeyboard(UserMessageManager.keyButtons));
                Start.telegramBot.execute(message);
            }


        }
        return MainBot.CONFIRMED_UPDATES_ALL;
    }
}
