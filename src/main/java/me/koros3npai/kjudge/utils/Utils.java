package me.koros3npai.kjudge.utils;

import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class Utils extends KJudge{

    public Utils() {
    }

    // A simple chat formatting function
    public static String chat(String s) {

        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // chops a list into non-view sublists of length L
    static <String> List<List<String>> chopped(List<String> list, final int L) {
        List<List<String>> parts = new ArrayList<List<String>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<String>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }


    // Nice little method to create a gui item with a custom name, and description
    public static ItemStack createGuiItem(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        List<String> metalore = new ArrayList<>();
        String l = String.join(" ", lore);

        if(l.length() >= 3000){
            metalore.add(l.substring(0, l.length()/40));
            metalore.add(l.substring(l.length()/40, 2*l.length()/40));
            metalore.add(l.substring(2*l.length()/40, 3*l.length()/40));
            metalore.add(l.substring(3*l.length()/40, 4*l.length()/40));
            metalore.add(l.substring(4*l.length()/40, 5*l.length()/40));
            metalore.add(l.substring(5*l.length()/40, 6*l.length()/40));
            metalore.add(l.substring(6*l.length()/40, 7*l.length()/40));
            metalore.add(l.substring(7*l.length()/40, 8*l.length()/40));
            metalore.add(l.substring(8*l.length()/40, 9*l.length()/40));
            metalore.add(l.substring(9*l.length()/40, 10*l.length()/40));
            metalore.add(l.substring(10*l.length()/40, 11*l.length()/40));
            metalore.add(l.substring(11*l.length()/40, 12*l.length()/40));
            metalore.add(l.substring(12*l.length()/40, 13*l.length()/40));
            metalore.add(l.substring(13*l.length()/40, 14*l.length()/40));
            metalore.add(l.substring(14*l.length()/40, 15*l.length()/40));
            metalore.add(l.substring(15*l.length()/40, 16*l.length()/40));
            metalore.add(l.substring(16*l.length()/40, 17*l.length()/40));
            metalore.add(l.substring(17*l.length()/40, 18*l.length()/40));
            metalore.add(l.substring(18*l.length()/40, 19*l.length()/40));
            metalore.add(l.substring(19*l.length()/40, 20*l.length()/40));
            metalore.add(l.substring(21*l.length()/40, 22*l.length()/40));
            metalore.add(l.substring(22*l.length()/40, 23*l.length()/40));
            metalore.add(l.substring(23*l.length()/40, 24*l.length()/40));
            metalore.add(l.substring(24*l.length()/40, 25*l.length()/40));
            metalore.add(l.substring(25*l.length()/40, 26*l.length()/40));
            metalore.add(l.substring(26*l.length()/40, 27*l.length()/40));
            metalore.add(l.substring(27*l.length()/40, 28*l.length()/40));
            metalore.add(l.substring(28*l.length()/40, 29*l.length()/40));
            metalore.add(l.substring(29*l.length()/40, 30*l.length()/40));
            metalore.add(l.substring(30*l.length()/40, 31*l.length()/40));
            metalore.add(l.substring(31*l.length()/40, 32*l.length()/40));
            metalore.add(l.substring(32*l.length()/40, 33*l.length()/40));
            metalore.add(l.substring(33*l.length()/40, 34*l.length()/40));
            metalore.add(l.substring(34*l.length()/40, 35*l.length()/40));
            metalore.add(l.substring(35*l.length()/40, 36*l.length()/40));
            metalore.add(l.substring(36*l.length()/40, 37*l.length()/40));
            metalore.add(l.substring(37*l.length()/40, 38*l.length()/40));
            metalore.add(l.substring(38*l.length()/40, 39*l.length()/40));
            metalore.add(l.substring(39*l.length()/40, l.length()));

        }

        if(l.length() >= 2000 && l.length() < 3000){
            metalore.add(l.substring(0, l.length()/30));
            metalore.add(l.substring(l.length()/30, 2*l.length()/30));
            metalore.add(l.substring(2*l.length()/30, 3*l.length()/30));
            metalore.add(l.substring(3*l.length()/30, 4*l.length()/30));
            metalore.add(l.substring(4*l.length()/30, 5*l.length()/30));
            metalore.add(l.substring(5*l.length()/30, 6*l.length()/30));
            metalore.add(l.substring(6*l.length()/30, 7*l.length()/30));
            metalore.add(l.substring(7*l.length()/30, 8*l.length()/30));
            metalore.add(l.substring(8*l.length()/30, 9*l.length()/30));
            metalore.add(l.substring(9*l.length()/30, 10*l.length()/30));
            metalore.add(l.substring(10*l.length()/30, 11*l.length()/30));
            metalore.add(l.substring(11*l.length()/30, 12*l.length()/30));
            metalore.add(l.substring(12*l.length()/30, 13*l.length()/30));
            metalore.add(l.substring(13*l.length()/30, 14*l.length()/30));
            metalore.add(l.substring(14*l.length()/30, 15*l.length()/30));
            metalore.add(l.substring(15*l.length()/30, 16*l.length()/30));
            metalore.add(l.substring(16*l.length()/30, 17*l.length()/30));
            metalore.add(l.substring(17*l.length()/30, 18*l.length()/30));
            metalore.add(l.substring(18*l.length()/30, 19*l.length()/30));
            metalore.add(l.substring(19*l.length()/30, 20*l.length()/30));
            metalore.add(l.substring(20*l.length()/30, 21*l.length()/30));
            metalore.add(l.substring(21*l.length()/30, 22*l.length()/30));
            metalore.add(l.substring(22*l.length()/30, 23*l.length()/30));
            metalore.add(l.substring(23*l.length()/30, 24*l.length()/30));
            metalore.add(l.substring(24*l.length()/30, 25*l.length()/30));
            metalore.add(l.substring(25*l.length()/30, 26*l.length()/30));
            metalore.add(l.substring(26*l.length()/30, 27*l.length()/30));
            metalore.add(l.substring(27*l.length()/30, 28*l.length()/30));
            metalore.add(l.substring(28*l.length()/30, 29*l.length()/30));
            metalore.add(l.substring(29*l.length()/30, l.length()));
        }

        if(l.length() >= 1100 && l.length() < 2000){
            metalore.add(l.substring(0, l.length()/20));
            metalore.add(l.substring(l.length()/20, 2*l.length()/20));
            metalore.add(l.substring(2*l.length()/20, 3*l.length()/20));
            metalore.add(l.substring(3*l.length()/20, 4*l.length()/20));
            metalore.add(l.substring(4*l.length()/20, 5*l.length()/20));
            metalore.add(l.substring(5*l.length()/20, 6*l.length()/20));
            metalore.add(l.substring(6*l.length()/20, 7*l.length()/20));
            metalore.add(l.substring(7*l.length()/20, 8*l.length()/20));
            metalore.add(l.substring(8*l.length()/20, 9*l.length()/20));
            metalore.add(l.substring(9*l.length()/20, 10*l.length()/20));
            metalore.add(l.substring(10*l.length()/20, 11*l.length()/20));
            metalore.add(l.substring(11*l.length()/20, 12*l.length()/20));
            metalore.add(l.substring(12*l.length()/20, 13*l.length()/20));
            metalore.add(l.substring(13*l.length()/20, 14*l.length()/20));
            metalore.add(l.substring(14*l.length()/20, 15*l.length()/20));
            metalore.add(l.substring(15*l.length()/20, 16*l.length()/20));
            metalore.add(l.substring(16*l.length()/20, 17*l.length()/20));
            metalore.add(l.substring(17*l.length()/20, 18*l.length()/20));
            metalore.add(l.substring(18*l.length()/20, 19*l.length()/20));
            metalore.add(l.substring(19*l.length()/20, l.length()));
        }


        if(l.length() >= 900 && l.length() < 1100){
            metalore.add(l.substring(0, l.length()/10));
            metalore.add(l.substring(l.length()/10, 2*l.length()/10));
            metalore.add(l.substring(2*l.length()/10, 3*l.length()/10));
            metalore.add(l.substring(3*l.length()/10, 4*l.length()/10));
            metalore.add(l.substring(4*l.length()/10, 5*l.length()/10));
            metalore.add(l.substring(5*l.length()/10, 6*l.length()/10));
            metalore.add(l.substring(6*l.length()/10, 7*l.length()/10));
            metalore.add(l.substring(7*l.length()/10, 8*l.length()/10));
            metalore.add(l.substring(8*l.length()/10, 9*l.length()/10));
            metalore.add(l.substring(9*l.length()/10, l.length()));
        }

        if(l.length() >= 700 && l.length() < 900){
            metalore.add(l.substring(0, l.length()/7));
            metalore.add(l.substring(l.length()/7, 2*l.length()/7));
            metalore.add(l.substring(2*l.length()/7, 3*l.length()/7));
            metalore.add(l.substring(3*l.length()/7, 4*l.length()/7));
            metalore.add(l.substring(4*l.length()/7, 5*l.length()/7));
            metalore.add(l.substring(5*l.length()/7, 6*l.length()/7));
            metalore.add(l.substring(6*l.length()/7, l.length()));
        }

        if(l.length() >= 400 && l.length() < 700){
            metalore.add(l.substring(0, l.length()/5));
            metalore.add(l.substring(l.length()/5, 2*l.length()/5));
            metalore.add(l.substring(2*l.length()/5, 3*l.length()/5));
            metalore.add(l.substring(3*l.length()/5, 4*l.length()/5));
            metalore.add(l.substring(4*l.length()/5, l.length()));
        }

        if(l.length() >= 100 && l.length() < 400){
            metalore.add(l.substring(0, l.length()/3));
            metalore.add(l.substring(l.length()/3, 2*l.length()/3));
            metalore.add(l.substring(2*l.length()/3, l.length()));
        }

        if(l.length() >= 75 && l.length() < 100){
            metalore.add(l.substring(0, l.length()/2));
            metalore.add(l.substring(l.length()/2, l.length()));
        }

        if(l.length() < 75) metalore.add(l);


        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(metalore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getHead(OfflinePlayer player, String... lore){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());

        List<String> l = new ArrayList<>();
        l.add(PlotUtils.printId((Player) player));
        l.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + SQLGetter.getPlotStatus(SQLGetter.getPlotIDPlayer((Player) player)));

        GetGroupLP gg = new GetGroupLP(getInstance(), getInstance().luckPerms);

        l.add(ChatColor.GRAY + "Rank:" + ChatColor.GOLD + gg.getPlayerGroup(player));

        meta.setLore(l);
        skull.setItemMeta(meta);

        return skull;
    }

    public static ItemStack getHead(OfflinePlayer player, String id){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());

        List<String> l = new ArrayList<>();
        l.add(id);
        if(PlotUtils.plotCheckNull((Player) player)) {
            l.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + SQLGetter.getPlotStatus(id));
            GetGroupLP gg = new GetGroupLP(getInstance(), getInstance().luckPerms);
            l.add(ChatColor.GRAY + "Rank: " + ChatColor.GOLD + gg.getPlayerGroup(player));
        }
        meta.setLore(l);
        skull.setItemMeta(meta);

        return skull;
    }

    public static OfflinePlayer getPlayerByID(String id){
        OfflinePlayer p = Bukkit.getServer().getPlayer(SQLGetter.getPlayerName(id));;
        if(p == null) {
            p = KJudge.getInstance().getServer().getOfflinePlayer(SQLGetter.getPlayerName(id));
            return p;
        }
        return p;
    }
}
