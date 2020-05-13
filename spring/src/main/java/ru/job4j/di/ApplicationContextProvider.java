package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextProvider {

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    public static AnnotationConfigApplicationContext getApplicationContext() {
        return context;
    }
}
