package bot;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class KeyboardBot {

    public static Keyboard defKeyboard(String[] keyButtons) {
        Keyboard keyboard = new ReplyKeyboardMarkup(keyButtons)
                .resizeKeyboard(true)
                .selective(true);
        return keyboard;
    }

    public static Keyboard defKeyboard(String keyButtons) {
        Keyboard keyboard = new ReplyKeyboardMarkup(keyButtons)
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        return keyboard;
    }
}
