package me.lizhaoguang.LLang;
import me.lizhaoguang.LLang.EOFToken;
import me.lizhaoguang.LLang.Token;

import java.io.IOException;

public class ParseException extends Exception {
    public ParseException(Token t) {
        this("", t);
    }
    public ParseException(String msg, Token t) {
        super("syntax error around " + location(t) + ". " + msg);
    }
    private static String location(Token t) {
        if (t == EOFToken.INSTANCE)
            return "the last lineNumber";
        else
            return "\"" + t.getText() + "\" at lineNumber " + t.getLineNumber();
    }
    public ParseException(IOException e) {
        super(e);
    }
    public ParseException(String msg) {
        super(msg);
    }
}
