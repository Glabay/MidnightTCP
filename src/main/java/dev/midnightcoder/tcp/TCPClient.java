package dev.midnightcoder.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Prof | Glabay-Studios
 * @project MidnightTCP
 * @social Discord: Prof
 * @since 2026-04-15
 */

public class TCPClient {
    public static Socket sock;

    void main(String[] args) {
        try {
            if (args.length != 2) {
                IO.println("Usage: TCPClient <address> <port>.");
                System.exit(-1);
            }
            String addr = args[0];
            int port = Integer.parseInt(args[1]);
            sock = new Socket();
            var inetAddress = new InetSocketAddress(addr, port);
            sock.connect(inetAddress, 0);
            if (inetAddress.isUnresolved()) {
                IO.println("Could not resolve address " + addr);
                System.exit(-1);
            }
            IO.println("Successfully connected!");
            handleConnection(sock);
        } catch (NumberFormatException e) {
            IO.println("Could not parse port from cmdline, port must be an integer");
            System.exit(-1);
        } catch (InterruptedException e) {
            IO.println("User cancelled!");
            System.exit(0);
        } catch (IOException e) {
            IO.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static void handleConnection(Socket connSock) throws IOException, InterruptedException {
        InputStream in = connSock.getInputStream();
        Thread inputThread = Thread.startVirtualThread(() -> {
            readFromInput(in);
        });
        OutputStream out = connSock.getOutputStream();
        Thread outputThread = Thread.startVirtualThread(() -> {
            writeToOutput(out);
        });
        inputThread.join();
        outputThread.join();
    }

    private static void readFromInput(InputStream in) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                IO.println(nextLine);
            }
        } catch (IOException e) {
            IO.println("Error reading from server.");
            IO.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeToOutput(OutputStream out) {
        try (PrintWriter writer = new PrintWriter(out, true);) {
            String nextLine;
            while ((nextLine = IO.readln(">")) != null) {
                writer.println(nextLine);
            }
        }
    }
}