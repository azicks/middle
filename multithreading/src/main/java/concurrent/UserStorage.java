package concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    static private class User {
        int id;
        int amount;
    }

    @GuardedBy("this")
    private List<User> users = new ArrayList<>();

    public synchronized boolean add(User user) {
        boolean result = false;
        if (user != null) {
            users.add(user.id, user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (user != null) {
            users.set(user.id, user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        if (user != null) {
            users.remove(user.id);
            result = true;
        }
        return result;
    }

    public synchronized boolean transfer(int fromID, int toID, int amount) {
        boolean result = false;
        if (amount > 0) {
            User fromUser = users.get(fromID);
            User toUser = users.get(toID);
            if (fromUser != null && toUser != null && fromUser.amount >= amount) {
                    fromUser.amount -= amount;
                    toUser.amount += amount;
                    result = true;
            }
        }
        return result;
    }
}
