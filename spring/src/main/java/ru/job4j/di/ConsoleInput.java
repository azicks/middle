package ru.job4j.di;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @since 11.12.2018
 */
public class ConsoleInput implements Input {

    private Consumer<String> output = System.out::print;

    @Override
    public String ask(String question) {
        Scanner scanner = new Scanner(System.in);
        output.accept(question + ": ");
        return scanner.nextLine();
    }

    @Override
    public void print(String data) {
        output.accept(data + System.lineSeparator());
    }
}
