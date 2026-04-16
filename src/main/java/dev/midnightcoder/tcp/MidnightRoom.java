package dev.midnightcoder.tcp;

import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightTCP
 * @social Discord: Glabay
 * @since 2026-04-15
 */
public class MidnightRoom {
    private final Map<String, PrintWriter> clients = new ConcurrentHashMap<>();

    public boolean join(String username, PrintWriter writer) {
        if (clients.containsKey(username)) {
            writer.println("Username already taken");
            return false;
        }
        clients.put(username, writer);
        return true;
    }

    public void broadcast(String sender, String message) {
        var formattedMessage = String.format("[%s] %s", sender, message);
        clients.values().forEach(writer ->
            writer.println(formattedMessage));
    }
}
