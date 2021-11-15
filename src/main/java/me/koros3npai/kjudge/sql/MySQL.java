package me.koros3npai.kjudge.sql;

import org.bukkit.Bukkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private final String hostname = Bukkit.getServer().getPluginManager().getPlugin("KJudge").getConfig().getString("hostname");
    private final String port = Bukkit.getServer().getPluginManager().getPlugin("KJudge").getConfig().getString("port");
    private final String database = Bukkit.getServer().getPluginManager().getPlugin("KJudge").getConfig().getString("database");
    private final String user = Bukkit.getServer().getPluginManager().getPlugin("KJudge").getConfig().getString("user");
    private final String password = Bukkit.getServer().getPluginManager().getPlugin("KJudge").getConfig().getString("password");

    private Connection connection;

    public Connection forceConnection() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.hostname + ':' + this.port + '/' + this.database + "?useSSL=false", this.user, this.password);

        return this.connection;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) {
            return this.connection;
        }
        return forceConnection();
    }

    public boolean checkConnection() throws SQLException {
        return (this.connection != null) && !this.connection.isClosed();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean closeConnection() throws SQLException {
        if (this.connection == null) {
            return false;
        }
        this.connection.close();
        this.connection = null;
        return true;
    }
}
