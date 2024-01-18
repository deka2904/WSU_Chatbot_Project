package com.github.tlaabs.chatbot;

public class Book {
    private String Message;
    private String Ner;


    public Book(String ner, String message)
    {
        Ner = ner;
        Message = message;

    }

    public String getNer() {
        return Ner;
    }

    public String getMessage() {
        return Message;
    }

}
