package com.miksinouf.chronowars.domain.board;

public class BadModelFormatException extends Exception {
    public BadModelFormatException(Integer lineNumber, String line, Integer charAt) {
        super(String.format("Bad format at " + lineNumber.toString() + ":" + charAt + "; unexpected char '" + line.charAt(charAt) + "' : " + line));
    }
}
