package com.gmail.borlandlp.minigamesdtools.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;

// взято откуда-то из интернетов и так и не трогал, и не использовал это, почти. переделать бы по нормальному.
public class SQLite extends Database {

    String dbname;

    public SQLite(){
        super(MinigamesDTools.getInstance());
        dbname = "twarena";
    }

    /*
    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS table_name (" +
            "`player` varchar(32) NOT NULL," +
            "`kills` int(11) NOT NULL," +
            "`total` int(11) NOT NULL," +
            "PRIMARY KEY (`player`)" +
            ");";
    */

    public String SQLiteCreatePlayersTable = "CREATE TABLE IF NOT EXISTS champions_rating_history (" +
            "`user_name` varchar(32) NOT NULL," +
            "`game_id` varchar(16) NOT NULL," +
            "`value` int(11) NOT NULL" +
            ");";

    public String SQLiteCreateChampionsGameTable = "CREATE TABLE IF NOT EXISTS champions_game (" +
            "`game_id` varchar(16) NOT NULL PRIMARY KEY," +
            "`arena_name` varchar(32) NOT NULL," +
            "`unixtime` int(11) NOT NULL" +
            ");";

    public String SQLiteCreateLocationCapturesTable = "CREATE TABLE IF NOT EXISTS location_captures (" +
            "`clan_uid` varchar(50) NOT NULL," +
            "`location_name` varchar(32) NOT NULL," +
            "`unixtime` int(11) NOT NULL" +
            ");";


    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname + ".db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
            }
        }

        try {
            if(connection != null && !connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initializeFromConfig", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }

        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreatePlayersTable);
            s.close();

            s = connection.createStatement();
            s.executeUpdate(SQLiteCreateChampionsGameTable);
            s.close();

            s = connection.createStatement();
            s.executeUpdate(SQLiteCreateLocationCapturesTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}