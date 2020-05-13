package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StartUI {
    private Store store;

    public StartUI(Store store) {
        this.store = store;
    }

    public void add(String value) {
        store.add(value);
    }

    public void print() {
        AnnotationConfigApplicationContext context = ApplicationContextProvider.getApplicationContext();
        ConsoleInput ci = context.getBean(ConsoleInput.class);
        for (String value : store.getAll()) {
            ci.print(value);
        }
    }
}
