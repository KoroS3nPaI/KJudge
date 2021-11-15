package me.koros3npai.kjudge.commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.PlotId;
import me.koros3npai.kjudge.KJudge;
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

public class SetPlotComment implements CommandExecutor {
    private KJudge plugin;

    public SetPlotComment(KJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("pcomment").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            this.plugin.getConfig().getString("Console.error");
        } else {
            Player p = (Player) sender;
            if(p.hasPermission("kjudge.pcomment")) {
                if (PlotUtils.plotCheckNull(p)) {
                    if (isSubmit(p)) {
                        if (args.length == 0) {
                            TextComponent comedit = new TextComponent(ChatColor.GREEN + "[Edit comment] ");
                            TextComponent comadd = new TextComponent(ChatColor.BLUE + "[Add to comment]");
                            comedit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pcomment edit "));
                            comedit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to edit comment").create()));
                            comadd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pcomment add "));
                            comadd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to add to comment").create()));
                            p.spigot().sendMessage(comedit, comadd);
                            return true;
                        }

                        if (args[0].equalsIgnoreCase("edit")) {
                            List<String> comment = new ArrayList<>();
                            for (String s : args) {
                                comment.add(s);
                            }
                            comment.remove(0);
                            SQLGetter.addPlotComment(PlotUtils.getId(p).toString(), String.join(" ", comment));
                            p.sendMessage(ChatColor.GOLD + "Comment updated");
                        }

                        if (args[0].equalsIgnoreCase("add")) {
                            List<String> comadd = new ArrayList<>();
                            List<String> orcom = new ArrayList<>();
                            orcom.add(SQLGetter.getPlotComment(PlotUtils.getId(p).toString()));
                            for (String s : args) {
                                comadd.add(s);
                            }
                            comadd.remove(0);
                            List<String> addedcom = Stream.of(orcom, comadd)
                                    .flatMap(x -> x.stream())
                                    .collect(Collectors.toList());
                            SQLGetter.addPlotComment(PlotUtils.getId(p).toString(), String.join(" ", addedcom));
                            p.sendMessage(ChatColor.GOLD + "Comment updated");
                        }
                    } else p.sendMessage(ChatColor.RED + "This plot is not submitted");
                } else p.sendMessage(ChatColor.RED + "You must stand on a plot");
            } else p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "kjudge.pcomment");
        }
        return false;
    }

    public Boolean isSubmit(Player p){
        PlotPlayer pp = BukkitUtil.adapt(p);
        PlotId id = pp.getCurrentPlot().getId();

        if( SQLGetter.getPlotIDSubmitted().contains(id.toString())){
            return true;
        } else return false;

    }
}
