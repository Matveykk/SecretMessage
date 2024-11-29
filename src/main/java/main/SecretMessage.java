package main;

import java.util.ArrayList;

public class SecretMessage {

    public static ArrayList<String> SendMessage(String message) {

        String hash = HashThis();

        ArrayList<String> array = new ArrayList<>();

        array.add(message);
        array.add(hash);

        return array;

    }

    private static String HashThis() {
        return (int) (Math.random() * 1000) + "@";
    }
}
