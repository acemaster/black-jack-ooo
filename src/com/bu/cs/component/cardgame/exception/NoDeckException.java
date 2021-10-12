package com.bu.cs.component.cardgame.exception;

public class NoDeckException extends Exception{

    public NoDeckException() {
        super("No decks with cards found");
    }
}
