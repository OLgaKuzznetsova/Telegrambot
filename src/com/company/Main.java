package com.company;

import java.util.Scanner;

public class Main {
    private  int chatId = 0;
    private BotLogic botLogic = new BotLogic();

    public static void main(String[] args) {
        Main main1 = new Main();
        Scanner input = new Scanner(System.in);
        System.out.println(main1.botLogic.handleUserInput(main1.chatId, ""));

        while (true) {
            var userInput = input.nextLine();
            var reply = main1.botLogic.handleUserInput(main1.getChatId(), userInput);
            System.out.println(reply);
        }
    }

    private int getChatId()
    {
        return chatId;
    }
}
