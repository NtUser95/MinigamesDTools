package com.gmail.borlandlp.minigamesdtools.database;

public enum Errors {
    sqlConnectionExecute("Couldn't execute MySQL statement: "),
    sqlConnectionClose("Failed to close MySQL connection: "),
    noSQLConnection("Unable to retreive MYSQL connection: "),
    noTableFound("Database Error: No Table Found");

    private String msg;

    Errors(String errMessage) {
        this.msg =  errMessage;
    }

    public String getMessage() {
        return this.msg;
    }
}