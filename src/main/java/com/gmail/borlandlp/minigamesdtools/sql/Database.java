package com.gmail.borlandlp.minigamesdtools.sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

// взято откуда-то из интернетов и так и не трогал, и не использовал это, почти. переделать бы по нормальному.
public abstract class Database {
    JavaPlugin plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "table_name";
    public int tokens = 0;
    public Database(JavaPlugin instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        /*try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }*/
    }

    /*
    * Example code
    public Integer getTokens(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '" + string + "';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())) {
                    return rs.getInt("kills");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getTotal(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public void setTokens(Player player, Integer tokens, Integer total) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,kills,total) VALUES(?,?,?)");
            ps.setString(1, player.getName().toLowerCase());

            ps.setInt(2, tokens);
            ps.setInt(3, total);
            ps.executeUpdate();

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;
    }
    */
    /*
    public String SQLiteCreatePlayersTable = "CREATE TABLE IF NOT EXISTS champions_rating_history (" +
            "`user_name` varchar(32) NOT NULL," +
            "`game_id` varchar(16) NOT NULL," +
            "`value` int(11) NOT NULL," +
            ");";

    public String SQLiteCreateChampionsGameTable = "CREATE TABLE IF NOT EXISTS champions_game (" +
            "`game_id` varchar(16) NOT NULL," +
            "`arena_name` varchar(32) NOT NULL," +
            "`unixtime` int(11) NOT NULL," +
            "PRIMARY KEY (`game_id`)" +
            ");";

    * */

    public int getArenaRating(Player player) {
        int rating = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();

            String query =
                    "SELECT" +
                    "  SUM(value) as value" +
                    " FROM" +
                    "  champions_rating_history" +
                    "  INNER JOIN champions_game on champions_game.game_id = champions_rating_history.game_id " +
                    "WHERE" +
                    "  champions_game.unixtime > ?" +
                    "  AND champions_rating_history.user_name = ?;";

            ps = conn.prepareStatement(query);
            ps.setLong(1, Instant.now().getEpochSecond() - (60 * 60 * 24 * 14));
            ps.setString(2, player.getName());
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                rating = result.getInt("value");
            }

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }

        return rating;
    }

    public void addArenaRating(ArenaBase arena, String username, int rating) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();

            ps = conn.prepareStatement("INSERT INTO champions_rating_history (user_name,game_id,value) VALUES(?,?,?)");
            ps.setString(1, username);
            ps.setString(2, arena.getGameId());
            ps.setInt(3, rating);
            ps.executeUpdate();

            ps = conn.prepareStatement("INSERT OR IGNORE INTO champions_game (game_id,arena_name,unixtime) VALUES(?,?,?)");
            ps.setString(1, arena.getGameId());
            ps.setString(2, arena.getName());
            ps.setLong(3, Instant.now().getEpochSecond());
            ps.executeUpdate();

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}