package norswap.utils;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Utility functions dealing with strings and string builders
 */
public final class Strings
{
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the last character of the string, or throws an exception if the string is empty.
     */
    public static char last_char (String string)
    {
        if (string.isEmpty())
            throw new IllegalArgumentException("empty string");
        return string.charAt(string.length() - 1);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns a string that repeats the character {@code c}, {@code n} times.
     */
    public static String repeat (char c, int n)
    {
        char[] chars = new char[n];
        for (int i = 0; i < n; ++i) chars[i] = c;
        return new String(chars);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returns an array of strings representing the individual lines in {@code string}. Each newline
     * character, as well as the end of the string, cause a new (possibly empty) line to be created.
     * The newlines characters are stripped from the lines.
     */
    public static String[] lines (String string)
    {
        ArrayList<String> lines = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '\n') {
                lines.add(string.substring(start, i));
                start = i + 1;
            }
        }
        lines.add(string.substring(start));
        return lines.toArray(new String[0]);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Prepend {@code prefix} to the start of each line of {@code string}. The lines should be
     * understood in the sense of {@link #lines(String)}, so if {@code string} ends with a newline,
     * the returned string will end with the prefix.
     */
    public static String indent (String string, String prefix)
    {
        String[] lines = lines(string);
        StringBuilder b = new StringBuilder();
        for (String line: lines)
            append(b, prefix, line, "\n");
        pop(b, 1); // last newline
        return b.toString();
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Prepend `prefix` to the start of each line of `string`, except the first. The lines should be
     * understood in the sense of `lines()`, so if `string` ends with a newline, the returned string
     * will end with the prefix.
     */
    public static String indent_except_first (String string, String prefix)
    {
        String[] lines = lines(string);
        StringBuilder b = new StringBuilder();
        append(b, lines[0], "\n");
        for (int i = 1; i < lines.length; ++i)
            append(b, prefix, lines[i], "\n");
        pop(b, 1); // last newline
        return b.toString();
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Appends all strings in {@code strings} to {@code b}.
     */
    public static void append (StringBuilder b, String... strings) {
        for (String string: strings) b.append(string);
    }

    // ---------------------------------------------------------------------------------------------


    /**
     * Removes the {@code n} last characters from {@code b}.
     * @throws NoSuchElementException if the string builder has less than {@code n} characters.
     */
    public static void pop (StringBuilder b, int n)
    {
        if (b.length() < n) throw new NoSuchElementException();
        b.replace(b.length() - n, b.length(), "");
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Adds the string representation of items in {@code items} to {@code b}, separting them
     * by the separator {@code sep}.
     */
    public static <T> void separated(StringBuilder b, String sep, T... items)
    {
        int length = b.length();
        for (T item: items)
            b.append(item).append(sep);
        if (b.length() > length)
            pop(b, sep.length());
    }

    // ---------------------------------------------------------------------------------------------
}
