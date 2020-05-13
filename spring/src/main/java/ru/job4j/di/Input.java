package ru.job4j.di;

import java.util.function.Consumer;

public interface Input {
    String ask(String question);
    void print(String data);
}
