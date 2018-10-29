package com.gmail.borlandlp.minigamesdtools.sql;

// взято откуда-то из интернетов и так и не трогал, и не использовал это, почти. переделать бы по нормальному.
public class Errors {

    public static String sqlConnectionExecute(){
        return "Couldn't execute MySQL statement: ";
    }

    public static String sqlConnectionClose(){
        return "Failed to close MySQL connection: ";
    }

    public static String noSQLConnection(){
        return "Unable to retreive MYSQL connection: ";
    }

    public static String noTableFound(){
        return "Database Error: No Table Found";
    }

}