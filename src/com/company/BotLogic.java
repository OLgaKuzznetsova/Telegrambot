package com.company;

import java.util.Random;
import java.util.Scanner;

public class BotLogic {
    private int[] states = new int[]{-1};
    //private String userInput;
    private Data data = new Data();
    private String[] words = data.getWords();
    private String[] definition = data.getDefinitions();
    private int counter = 0;

    public String handleUserInput(int chatId, String userInput) {
        if (states[0] == -1) {
            states[0] = 0;
            return data.getMainMenu();
        }
        if (userInput.equalsIgnoreCase("Определения")) {
            states[0] = 1;
            return data.getMenu1();
        }
        //if (userInput.equalsIgnoreCase("Тест")) {
            //states[0] = 2;
            //return data.getMenu2();
        //}
        if (userInput.equalsIgnoreCase("Меню")) {
            states[0] = 0;
            return data.getMenu();
        }
        if (states[0] == 1)
            return optionOne(userInput);
        //if (states[0] == 2)
            //return optionTwo(userInput);

        return "Данные введены неверно";
    }

    public String optionOne(String userInput) {
        for (int i = 0; i < words.length; i++) {
            if (userInput.equalsIgnoreCase(words[i])) {
                return words[i] + "-" + definition[i];
            }
        }
        return "Нет такого определения.";
    }
//fjfjfjfjfjff
    /*public String optionTwo(String userInput) {
        String answer = "";
        if (states[1] == 0) {
            states[1] = 1;
            answer = words[counter];
            return definition[counter] + "\n";
        }
        if (states[1] == 1) {
            states[1] = 0;
            if (words.length - 1 == counter) {
                counter = 0;
            } else {
                counter++;
            }
            if (userInput.equalsIgnoreCase(answer)) {
                return "Правильный ответ";
            } else {
                return ("Неправильный ответ! Правильный ответ - " + answer);

            }
        }
        return "";
    }*/
}