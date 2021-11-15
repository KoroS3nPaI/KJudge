package me.koros3npai.kjudge.commands;

import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.GUIinit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Judge implements CommandExecutor {
    private KJudge plugin;

    public Judge(KJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("judge").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
        } else {
            Player player = (Player) sender;
            if(player.hasPermission("kjudge.judge")) {
                GUIinit.GUIJudge(player);
                player.openInventory(GUIinit.invjudge);
            } else player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.judge");
        }

        return true;
    }
}
