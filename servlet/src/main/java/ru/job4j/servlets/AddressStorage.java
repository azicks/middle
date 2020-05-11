package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlets.store.DBStore;
import ru.job4j.servlets.store.Store;

import java.util.Set;

public class AddressStorage {
    private static final AddressStorage instance = new AddressStorage();
    private final Store store = DBStore.getInstance();
    private static final Logger log = LogManager.getLogger(UserStorage.class);

    private AddressStorage() {

    }

    public static AddressStorage getInstance() {
        return instance;
    }

    public Set<String> getAllCities() {
        return store.findAllCities();
    }
}
