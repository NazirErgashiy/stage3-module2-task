package com.mjc.school;

import com.mjc.school.controller.AuthorControllerRequest;
import com.mjc.school.controller.NewsControllerRequest;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        NewsController newsController = context.getBean("newsControllerBean", NewsController.class);
        AuthorController authorController = context.getBean("authorControllerBean", AuthorController.class);
        context.close();

        authorController.createTestDataBase();
        newsController.createTestDataBase();

        boolean exit = false;
        Scanner reader = new Scanner(System.in);

        Map<Integer, Command> commandsMap = new HashMap<>();
        commandsMap.put(1, () -> String.valueOf(newsController.readAll()));
        commandsMap.put(2, () -> String.valueOf(authorController.readAll()));
        commandsMap.put(3, () -> String.valueOf(newsController.readById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(4, () -> String.valueOf(authorController.readById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(5, () -> String.valueOf(newsController.create(newsRequest(getUserInputAsString("Input title:", reader),
                getUserInputAsString("Input content", reader),
                (long) getUserInputAsInt("Input author id:", reader)))));
        commandsMap.put(6, () -> String.valueOf(authorController.create(authorRequest(getUserInputAsString("Input author name", reader)))));
        commandsMap.put(7, () -> String.valueOf(newsController.update(newsRequest(getUserInputAsString("Input title:", reader),
                getUserInputAsString("Input content", reader),
                (long) getUserInputAsInt("Input author id:", reader)))));
        commandsMap.put(8, () -> String.valueOf(authorController.update(authorRequest(getUserInputAsString("Input author name", reader)))));
        commandsMap.put(9, () -> String.valueOf(newsController.deleteById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(10, () -> String.valueOf(authorController.deleteById((long) getUserInputAsInt("Input id:", reader))));

        while (!exit) {
            showMenu();
            int inputKey = reader.nextInt();

            if (commandsMap.containsKey(inputKey)) {
                catchThis(commandsMap.get(inputKey));
            } else {
                if (inputKey == 0) {
                    exit = true;

                } else {
                    System.out.println("Unsupported operation");
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("Enter the number of operation:");
        System.out.println("1 - Get all news.");
        System.out.println("2 - Get all authors.");
        System.out.println("3 - Get news by id.");
        System.out.println("4 - Get author by id.");
        System.out.println("5 - Create news.");
        System.out.println("6 - Create author.");
        System.out.println("7 - Update news.");
        System.out.println("8 - Update author.");
        System.out.println("9 - Remove news by id.");
        System.out.println("10 - Remove author by id.");
        System.out.println("0 - Exit.");
    }

    public interface Command {
        String run();
    }

    private static void catchThis(Command command) {
        try {
            String actionValueOf = command.run();
            System.out.println(actionValueOf);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getUserInputAsInt(String message, Scanner reader) {
        System.out.println(message);
        try {
            return reader.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private static String getUserInputAsString(String message, Scanner reader) {
        System.out.println(message);
        try {
            return reader.next();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static NewsControllerRequest newsRequest(String title, String content, Long id) {
        NewsControllerRequest req = new NewsControllerRequest();
        req.setTitle(title);
        req.setContent(content);
        req.setAuthorId(id);
        return req;
    }

    private static AuthorControllerRequest authorRequest(String name) {
        AuthorControllerRequest req = new AuthorControllerRequest();
        req.setName(name);
        return req;
    }
}

