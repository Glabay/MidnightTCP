package dev.midnightcoder.tcp;

import java.io.PrintWriter;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightTCP
 * @social Discord: Glabay
 * @since 2026-04-15
 */
public interface ICommand {
    void execute(PrintWriter writer, String... args);
}
