package me.koros3npai.kjudge.commands;

import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.classes.PlotSubmit;
import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.GUIinit;
import me.koros3npai.kjudge.utils.PlotUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Submit implements CommandExecutor {
    private KJudge plugin;

    public Submit(KJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("submit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
        } else {
            Player p = (Player) sender;
            if(p.hasPermission("kjudge.submit")) {
                PlotSubmit.setPlotTable(p);
                if (PlotUtils.plotCheck(p)) {
                    SQLGetter.addPlotID(p.getUniqueId(), PlotUtils.getId(p).toString());
                    GUIinit.GUI(p);
                } else p.sendMessage(ChatColor.RED + "You must stand on your plot");
                p.openInventory(GUIinit.inv);
            } else p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.submit");
        }

        return true;
    }

}
