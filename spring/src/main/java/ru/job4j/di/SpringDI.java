package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = ApplicationContextProvider.getApplicationContext();
        context.scan("ru.job4j.di");
        context.refresh();

        StartUI ui = context.getBean(StartUI.class);

        ui.add("Ivan Ivanov");
        ui.add("Petr Ptrov");
        ui.add("Sidr Sidorov");

        ui.print();
    }
}

