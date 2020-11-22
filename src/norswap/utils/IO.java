package norswap.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Input/Output related utilities.
 */
public final class IO
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a string with the file contents, or null if the file could not be read.
     */
    public static String slurp (String file)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        }
        catch (IOException e) {
            return null;
        }
    }

    // ---------------------------------------------------------------------------------------------
}

