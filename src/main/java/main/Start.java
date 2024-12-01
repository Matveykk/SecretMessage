package main;

import workWithDB.MainDBWork;

import java.sql.SQLException;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("Что вы хотите сделать? (Введите \"Получить сообщение\" или \"Отправить сообщение\" или \"Выход\")");
            switch (sc.nextLine()) {
                case "Получить сообщение":
                    System.out.println("Введите код: ");
                    try {
                        System.out.println(MainDBWork.takeFromDB(sc.nextLine()));
                    } catch (Exception e) {
                        System.out.println("Код не найден");
                    }
                    break;

                case "Отправить сообщение":
                    System.out.println("Введите сообщение: ");
                    System.out.println("Ваш код: " + MainDBWork.saveToDB(sc.nextLine()));
                    break;

                case "Выход":
                    flag = false;
            }
        }
    }
}

