package dev.midnightcoder.tcp;

import java.io.PrintWriter;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightTCP
 * @social Discord: Glabay
 * @since 2026-04-15
 */
public class Help implements ICommand {
    @Override
    public void execute(PrintWriter out, String... args) {
        out.println("Available commands:");
        out.println("- help: Display this help message");
        out.println("- join <room_name>: Join a room");
        out.println("- leave: Leave the current room");
        out.println("- say <message>: Send a message to the room");
    }
}
