package ru.job4j.di;

public class Main {
    public static void main(String[] args) {
        Context context = Context.getInstance();
        context.reg(Store.class);
        context.reg(StartUI.class);
        context.reg(ConsoleInput.class);

        StartUI ui = context.get(StartUI.class);

        ui.add("Ivan Ivanov");
        ui.add("Petr Ptrov");
        ui.add("Sidr Sidorov");

        ui.print();
    }
}