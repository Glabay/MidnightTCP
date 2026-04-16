package dev.midnightcoder.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightTCP
 * @social Discord: Glabay
 * @since 2026-04-15
 */
public class MidnightSession implements Runnable {
    private final Socket socket;
    private final MidnightRoom room;

    public MidnightSession(Socket socket, MidnightRoom room) {
        this.socket = socket;
        this.room = room;
    }

    @Override
    public void run() {
        try (
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("=======================");
            out.println("Welcome to MidnightTCP!");
            out.println("=======================");
            out.println("Enter a username:");
            var username = in.readLine();
            var joined = room.join(username, out);
            if (!joined) return;

            room.broadcast(username, "joined the room");
            String line;
            while ((line = in.readLine()) != null) {
                if (parsedCommand(line, out)) continue;
                room.broadcast(username, line);
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private boolean parsedCommand(String command, PrintWriter writer) {
        var cmd = command.toLowerCase();
        if (cmd.startsWith("/help")) {
            new Help().execute(writer, cmd);
            return true;
        }
        if (cmd.startsWith("/exit") ||
            cmd.startsWith("/quit") ||
            cmd.startsWith("/disconnect")) {
//            new Exit().execute(writer, cmd);
            return true;
        }
        return false;
    }
}
