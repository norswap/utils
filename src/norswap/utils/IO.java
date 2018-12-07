package norswap.utils;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Input/Output related utilities.
 */
public final class IO
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a list of all the paths that match the given glob pattern within the given directory.
     *
     * <p>The pattern syntax is described in the doc of {@link FileSystem#getPathMatcher(String)} —
     * the "glob:" part should be omitted.
     */
    public static List<Path> glob (String pattern, Path directory) throws IOException
    {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        List<Path> result = new ArrayList<>();

        Files.walkFileTree (directory, new SimpleFileVisitor<Path>()
        {
            @Override public FileVisitResult visitFile (Path file, BasicFileAttributes attrs)
            {
                if (matcher.matches(file)) result.add(file);
                return FileVisitResult.CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed (Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        return result;
    }

    // ---------------------------------------------------------------------------------------------
}

