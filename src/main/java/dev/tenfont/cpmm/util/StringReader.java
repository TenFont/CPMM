package dev.tenfont.cpmm.util;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

@Getter
public class StringReader implements Cloneable {
    private final String string;
    @Setter
    private int cursor;

    public StringReader(String string) {
        this.string = string;
        this.cursor = 0;
    }

    public String finish() {
        String remaining = string.substring(cursor);
        cursor = string.length();
        return remaining;
    }

    public String readUntil(Predicate<Character> predicate) {
        if (isEndOfFile())
            return "";
        StringBuilder builder = new StringBuilder();
        while (!this.isEndOfFile() && !predicate.test(this.peekChar()))
            builder.append(this.readChar());
        return builder.toString();
    }

    public String readString(int amount) {
        return this.string.substring(this.cursor, this.cursor += amount);
    }

    public String peekString(int amount) {
        return this.string.substring(this.cursor, this.cursor + amount);
    }

    public char readChar() {
        return this.string.charAt(this.cursor++);
    }

    public char peekChar() {
        return this.string.charAt(this.cursor);
    }

    public boolean read(String match) {
        return this.read(match, false);
    }

    public boolean read(String match, boolean ignoreCase) {
        if (!this.canRead(match.length()))
            return false;
        String read = this.readString(match.length());
        return ignoreCase ? match.equalsIgnoreCase(read) : match.equals(read);
    }

    public boolean canRead(int amount) {
        return this.cursor + amount < string.length();
    }

    public boolean isEndOfFile() {
        return this.cursor == string.length();
    }

    @Override
    public StringReader clone() {
        try {
            return (StringReader) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
