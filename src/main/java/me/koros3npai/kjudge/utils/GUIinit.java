package me.koros3npai.kjudge.utils;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import me.koros3npai.kjudge.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GUIinit {
    public static Inventory inv;
    public static Inventory invconfirm;
    public static Inventory invjudge;
    public static Inventory invjudgesubplot;
    public static Inventory invplotinfo;
    public static Inventory invplayerplots;
    public static Inventory invplotinfojudge;
    public static Inventory invsetrank;
    public static Inventory invdeny;

    public GUIinit(){

    }

    public static void GUI(Player p) {
        // Inventories, with no owner
        inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Plot Overview");
        invconfirm = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Submission");
        invplotinfo = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Plot Info");
        invplayerplots = Bukkit.createInventory(null, 9, ChatColor.DARK_BLUE + "Submitted Plots");

        // Put the items into the inventory
        initializeItems(p);

    }

    public static void GUIJudge(Player player){
        invjudge = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + "Judge Panel");
        invplotinfojudge = Bukkit.createInventory(null, 9, ChatColor.DARK_BLUE + "Plot info");
        invsetrank = Bukkit.createInventory(null, 18, ChatColor.RED + "Ranks");
        invdeny = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Deny plot");

        initSubmittedPlot(player);

        PlotPlayer p = BukkitUtil.getPlayer(player);//BukkitUtil.adapt(p);
        String id = p.getCurrentPlot().getId().toString();
        boolean exists =  SQLGetter.exists(UUID.nameUUIDFromBytes(id.getBytes()));

        if(PlotUtils.plotCheckNull(player) && exists){
            initInfoJudge(player);
        }
    }

    // Item placement
    public static void initializeItems(Player p) {

        //Items for the plot overview GUI
        inv.setItem(0, Utils.getHead(p));
        inv.setItem(3, Utils.createGuiItem(Material.WRITABLE_BOOK, Utils.chat(ChatColor.YELLOW + "List of your submitted plots"), 1));
        if(PlotUtils.plotCheck(p)){
        inv.setItem(5, Utils.createGuiItem(Material.PAPER, Utils.chat(
                ChatColor.YELLOW + "Plot Submission Info"), 1, "Title: " + SQLGetter.getPlotTitle(PlotUtils.getId(p).toString())));}
        else { inv.setItem(5, Utils.createGuiItem(Material.PAPER, Utils.chat(
                ChatColor.YELLOW + "Plot Submission Info"), 1, "You must be on your plot"));}
        inv.setItem(8, Utils.createGuiItem(Material.GREEN_CONCRETE, ChatColor.DARK_GREEN + "Submit Plot", 1, ""));

        //Items for the confirmation GUI
        invconfirm.setItem(3, Utils.createGuiItem(Material.GREEN_CONCRETE, ChatColor.DARK_GREEN + "Submit Plot", 1, ChatColor.GOLD + "Are you sure you want to submit this plot?"));
        invconfirm.setItem(5, Utils.createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "Cancel", 1, ""));

        //Items for the Plot Info GUI
        invplotinfo.setItem(1, Utils.getHead(p));
        invplotinfo.setItem(3, Utils.createGuiItem(Material.OAK_SIGN, ChatColor.GOLD + "Edit Title:", 1, SQLGetter.getPlotTitle(PlotUtils.getId(p).toString())));
        invplotinfo.setItem(5, Utils.createGuiItem(Material.WRITABLE_BOOK, ChatColor.GOLD + "Edit Lore:", 1, SQLGetter.getPlotLore(PlotUtils.getId(p).toString())));
        invplotinfo.setItem(7, Utils.createGuiItem(Material.PAPER, ChatColor.GRAY + "Comments:", 1, SQLGetter.getPlotComment(PlotUtils.getId(p).toString())));

        //Items for player's submitted plots
        for(int i = 0; i < SQLGetter.getPlayerPlotIDSubmitted(p).size(); i++){
            invplayerplots.addItem(Utils.createGuiItem(Material.SPRUCE_SIGN, SQLGetter.getPlayerPlotIDSubmitted(p).get(i), 1));
        }

    }

    public static void initSubmittedPlot(Player player){
        String idmsg;
        if(PlotUtils.plotCheckNull(player)) idmsg = "Plot ID: " + PlotUtils.getId(player).toString();
        else idmsg = ChatColor.RED + "You must stand on a plot";

        //Items for the judge panel GUI
        invjudge.setItem(3, Utils.createGuiItem(Material.WRITABLE_BOOK,ChatColor.YELLOW + "List of submitted plots", 1, ""));
        invjudge.setItem(5, Utils.createGuiItem(Material.PAPER,ChatColor.YELLOW + "Plot info", 1,idmsg));

        //Set Rank/Accept plot GUI
        invsetrank.setItem(2, Utils.createGuiItem(Material.ORANGE_BANNER, ChatColor.GOLD + "Novice", 1));
        invsetrank.setItem(4, Utils.createGuiItem(Material.BLUE_BANNER, ChatColor.BLUE + "Disciple", 1));
        invsetrank.setItem(6, Utils.createGuiItem(Material.GREEN_BANNER, ChatColor.GREEN + "Mentor", 1));
        invsetrank.setItem(11, Utils.createGuiItem(Material.CYAN_BANNER, ChatColor.AQUA + "Guru", 1));
        invsetrank.setItem(13, Utils.createGuiItem(Material.PURPLE_BANNER, ChatColor.DARK_PURPLE + "Expert", 1));
        invsetrank.setItem(15, Utils.createGuiItem(Material.RED_BANNER, ChatColor.RED + "Master", 1));

        //Deny plot confirmation GUI
        invdeny.setItem(3, Utils.createGuiItem(Material.GREEN_CONCRETE, ChatColor.GREEN + "Confirm", 1));
        invdeny.setItem(5, Utils.createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "Cancel", 1));
    }

    public static void initInfoJudge(Player player){
        OfflinePlayer ptarget = Utils.getPlayerByID(PlotUtils.getId(player).toString());

        invplotinfojudge.setItem(0, Utils.getHead(ptarget, PlotUtils.getId(player).toString()));
        invplotinfojudge.setItem(1, Utils.createGuiItem(Material.REDSTONE_TORCH,ChatColor.GREEN + "Set rank", 1, ChatColor.YELLOW + "Accept Plot"));
        invplotinfojudge.setItem(3, Utils.createGuiItem(Material.OAK_SIGN, ChatColor.GOLD + "Title:", 1, SQLGetter.getPlotTitle(PlotUtils.getId(player).toString())));
        invplotinfojudge.setItem(5, Utils.createGuiItem(Material.WRITABLE_BOOK, ChatColor.GOLD + "Lore:", 1, SQLGetter.getPlotLore(PlotUtils.getId(player).toString())));
        invplotinfojudge.setItem(7, Utils.createGuiItem(Material.PAPER,  "Comments:", 1,SQLGetter.getPlotComment(PlotUtils.getId(player).toString())));
        invplotinfojudge.setItem(8, Utils.createGuiItem(Material.BARRIER,ChatColor.GRAY + "Deny plot", 1));
    }
}
