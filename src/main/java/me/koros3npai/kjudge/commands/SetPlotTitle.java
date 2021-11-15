package me.koros3npai.kjudge.commands;

import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.classes.PlotSubmit;
import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.PlotUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class SetPlotTitle implements CommandExecutor {
    private KJudge plugin;

    public SetPlotTitle(KJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("ptitle").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)){
            this.plugin.getConfig().getString("Console.error");
        } else {
            Player p = (Player) sender;
            if(p.hasPermission("kjudge.ptitle")) {
                if (PlotUtils.plotCheck(p)) {
                    PlotSubmit.setPlotTable(p);
                    if (args.length == 0)
                        p.sendMessage(ChatColor.GREEN + "Input a title for you plot: /ptitle [title]");

                    List<String> title = new ArrayList<>();
                    for (String s : args) {
                        title.add(s);
                        SQLGetter.addPlotTitle(PlotUtils.getId(p).toString(), String.join(" ", title));
                    }

                    p.sendMessage(ChatColor.GOLD + "Title updated");

                } else p.sendMessage(ChatColor.RED + "You must be on your plot");
            } else p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.ptitle");
        }
        return true;
    }
}
