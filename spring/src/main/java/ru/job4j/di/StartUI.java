package ru.job4j.di;

public class StartUI {
    private Store store;
    private Context context = Context.getInstance();

    public StartUI(Store store) {
        this.store = store;
    }

    public void add(String value) {
        store.add(value);
    }

    public void print() {
        ConsoleInput ci = context.get(ConsoleInput.class);
        for (String value : store.getAll()) {
            ci.print(value);
        }
    }
}
