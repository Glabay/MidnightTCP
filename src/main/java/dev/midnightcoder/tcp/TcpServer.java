package dev.midnightcoder.tcp;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightTCP
 * @social Discord: Glabay
 * @since 2026-04-15
 */
public class TcpServer {

    void main() {
        var room = new MidnightRoom();

        try (var server = new ServerSocket(42069)) {
            IO.println("Server started on port " + server.getLocalPort());

            while (true) {
                var client = server.accept();
                Thread.startVirtualThread(new MidnightSession(client, room));
            }
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
