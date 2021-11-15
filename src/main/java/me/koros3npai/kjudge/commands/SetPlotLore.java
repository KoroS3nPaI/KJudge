package me.koros3npai.kjudge.commands;

import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.classes.PlotSubmit;
import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.PlotUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetPlotLore implements CommandExecutor {
    private KJudge plugin;

    public SetPlotLore(KJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("plore").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            this.plugin.getConfig().getString("Console.error");
        } else {
            Player p = (Player) sender;
            if(p.hasPermission("kjudge.plore")) {
                if (PlotUtils.plotCheck(p)) {
                    PlotSubmit.setPlotTable(p);
                    if (args.length == 0) {
                        TextComponent loreq = new TextComponent(ChatColor.GREEN + "[Edit plot lore] ");
                        TextComponent loreadd = new TextComponent(ChatColor.BLUE + "[Add to lore]");
                        loreq.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore edit "));
                        loreq.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to edit lore").create()));
                        loreadd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore add "));
                        loreadd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to add to lore").create()));
                        p.spigot().sendMessage(loreq, loreadd);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("edit")) {
                        List<String> lore = new ArrayList<>();
                        for (String s : args) {
                            lore.add(s);
                        }
                        lore.remove(0);
                        SQLGetter.addPlotLore(PlotUtils.getId(p).toString(), String.join(" ", lore));
                        p.sendMessage(ChatColor.GOLD + "Lore updated");
                    }

                    if (args[0].equalsIgnoreCase("add")) {
                        List<String> loreadd = new ArrayList<>();
                        List<String> orlore = new ArrayList<>();
                        orlore.add(SQLGetter.getPlotLore(PlotUtils.getId(p).toString()));
                        for (String s : args) {
                            loreadd.add(s);
                        }
                        loreadd.remove(0);
                        List<String> addedlore = Stream.of(orlore, loreadd)
                                .flatMap(x -> x.stream())
                                .collect(Collectors.toList());
                        SQLGetter.addPlotLore(PlotUtils.getId(p).toString(), String.join(" ", addedlore));
                        p.sendMessage(ChatColor.GOLD + "Lore updated");
                    }
                } else p.sendMessage(ChatColor.RED + "You must be on your plot");
            } else p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.plore");
        }
        return false;
    }
}
