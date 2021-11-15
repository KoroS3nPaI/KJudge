package me.koros3npai.kjudge;

import me.koros3npai.kjudge.classes.PlotSubmit;
import me.koros3npai.kjudge.commands.*;
import me.koros3npai.kjudge.listeners.GUIlistener;
import me.koros3npai.kjudge.listeners.MenuListener;
import me.koros3npai.kjudge.menu.PlayerMenuUtility;
import me.koros3npai.kjudge.utils.GetGroupLP;
import me.koros3npai.kjudge.utils.PlotUtils;
import me.koros3npai.kjudge.sql.MySQL;
import me.koros3npai.kjudge.sql.SQLGetter;
import com.plotsquared.core.PlotAPI;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.HashMap;

public class KJudge extends JavaPlugin implements Listener {
    public static SQLGetter data;
    public MySQL SQL;

    public LuckPerms luckPerms;

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Enabling kJudge!");
        Bukkit.getConsoleSender().sendMessage("Version: 2.6");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        new Submit(this);
        new GUIlistener(this);
        new Judge(this);
        new PlotSubmit();
        new SetPlotTitle(this);
        new SetPlotLore(this);
        new SetPlotComment(this);
        new MenuListener(this);
        new PlotAPI().registerListener(new PlotUtils());

        // Load an instance of 'LuckPerms' using the services manager.
        this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        new GetGroupLP(this, this.luckPerms);

        this.SQL = new MySQL();
        this.data = new SQLGetter(this);

        try {
            SQL.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database not connected.");
        }

        try {
            if (SQL.checkConnection()){
                Bukkit.getLogger().info("Database is connected.");
                data.createTable();
                this.getServer().getPluginManager().registerEvents(this, this);
            }
        } catch (SQLException e) {

        }

    }

    //Provide a player and return a menu system for that player
    //create one if they don't already have one
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them add add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Disabling kJudge!");
        try {
            SQL.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static KJudge getInstance() {
        return (KJudge) getPlugin(KJudge.class);
    }
}
