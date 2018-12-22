package com.gmail.borlandlp.minigamesdtools.database;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.database.tables.Account;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class ExampleDatabaseInteraction implements APIComponent {
    private ConnectionSource connectionSource;

    @Override
    public void onLoad() {
        File dataFolder = new File(MinigamesDTools.getInstance().getDataFolder(),  "test.db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                MinigamesDTools.getInstance().getLogger().log(Level.SEVERE, "File write error: " + "test.db");
            }
        }

        String databaseUrl = "jdbc:sqlite:" + dataFolder.getAbsolutePath();
        try {
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
            TableUtils.createTable(connectionSource, Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnload() {
        try {
            this.connectionSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
