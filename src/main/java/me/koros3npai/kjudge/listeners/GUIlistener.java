package me.koros3npai.kjudge.listeners;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.menu.SubmittedMenu;
import me.koros3npai.kjudge.utils.GUIinit;
import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.PlotUtils;
import me.koros3npai.kjudge.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUIlistener implements Listener {
    private KJudge plugin;
    public static SQLGetter data;

    public GUIlistener(KJudge plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.data = new SQLGetter(this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (Objects.equals(e.getClickedInventory(), GUIinit.inv)){

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()){
                case WRITABLE_BOOK -> p.openInventory(GUIinit.invplayerplots);
                case PAPER -> {
                    if(PlotUtils.plotCheck(p)){ p.openInventory(GUIinit.invplotinfo);}
                    else p.sendMessage( ChatColor.RED + "This is not your plot");
                }
                case GREEN_CONCRETE -> {
                    if(PlotUtils.plotCheck(p)){ p.openInventory(GUIinit.invconfirm);}
                    else p.sendMessage( ChatColor.RED + "This is not your plot");
                }
            }

            e.setCancelled(true);
        }

        if(Objects.equals(e.getClickedInventory(), GUIinit.invconfirm)){

            PlotPlayer player = BukkitUtil.getPlayer(p);//BukkitUtil.adapt(p);
            String id = player.getCurrentPlot().getId().toString();
            List<String> status = new ArrayList<>();
            status.add(SQLGetter.getPlotStatus(id));

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case GREEN_CONCRETE -> {
                    if(!(status.contains("ACCEPTED"))) {
                        p.sendMessage(ChatColor.GOLD + "Plot submitted!");
                        SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "PENDING");
                        p.closeInventory();
                    } else p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                }
                case RED_CONCRETE -> p.openInventory(GUIinit.inv);
            }

            e.setCancelled(true);
        }

        if (Objects.equals(e.getClickedInventory(), GUIinit.invjudge)) {

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case WRITABLE_BOOK -> new SubmittedMenu(KJudge.getPlayerMenuUtility(p)).open();
                case PAPER -> {
                    if(PlotUtils.plotCheckNull(p)){
                        GUIinit.GUIJudge(p);
                        p.openInventory(GUIinit.invplotinfojudge);
                    } else p.sendMessage(ChatColor.RED + "You must stand on a plot");

                }
            }

            e.setCancelled(true);
        }

        if(Objects.equals(e.getClickedInventory(), GUIinit.invplotinfo)){

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case OAK_SIGN -> {
                    p.closeInventory();
                    TextComponent titleq = new TextComponent(ChatColor.GREEN + "[Edit plot title]");
                    titleq.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ptitle "));
                    titleq.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to edit title").create()));
                    p.spigot().sendMessage(titleq);

                }
                case WRITABLE_BOOK -> {
                    p.closeInventory();
                    p.performCommand("plore");
                }
            }

            e.setCancelled(true);
        }

        if (Objects.equals(e.getClickedInventory(), GUIinit.invjudgesubplot)) {

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case PLAYER_HEAD ->  p.performCommand("p v " + String.join("", e.getCurrentItem().getItemMeta().getLore()));
            }

            e.setCancelled(true);
        }

        if (Objects.equals(e.getClickedInventory(), GUIinit.invplayerplots)) {

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case SPRUCE_SIGN ->  p.performCommand("p v " + e.getCurrentItem().getItemMeta().getDisplayName());
            }

            e.setCancelled(true);
        }

        if (Objects.equals(e.getClickedInventory(), GUIinit.invplotinfojudge)) {

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case PAPER -> {
                    p.closeInventory();
                    p.performCommand("pcomment");
                }
                case REDSTONE_TORCH -> p.openInventory(GUIinit.invsetrank);
                case BARRIER -> p.openInventory(GUIinit.invdeny);
            }

            e.setCancelled(true);
        }

        if (Objects.equals(e.getClickedInventory(), GUIinit.invsetrank)) {
            OfflinePlayer ptarget = Utils.getPlayerByID(PlotUtils.getId(p).toString());
            String name = ptarget.getName();

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {

                case ORANGE_BANNER -> {
                    p.closeInventory();
                    p.performCommand("lp user "+name+" parent add novice");
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Novice");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");

                }
                case BLUE_BANNER -> {
                    p.closeInventory();
                    p.performCommand("lp user "+name+" parent add disciple");
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Disciple");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                }
                case GREEN_BANNER -> {
                    p.closeInventory();
                    p.performCommand("lp user "+name+" parent add mentor");
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Mentor");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                }
                case CYAN_BANNER -> {
                    p.closeInventory();
                    p.performCommand("lp user "+name+" parent add guru");
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Guru");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                }
                case PURPLE_BANNER -> {
                    p.closeInventory();
                    p.performCommand("lp user "+name+" parent add expert");
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Expert");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                }
                case RED_BANNER -> {
                    p.closeInventory();
                    p.performCommand("lp user "+name+" parent add master");
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Master");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                }
            }

            e.setCancelled(true);
        }

        if (Objects.equals(e.getClickedInventory(), GUIinit.invdeny)) {

            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }

            switch (e.getCurrentItem().getType()) {
                case GREEN_CONCRETE -> {
                    p.closeInventory();
                    p.sendMessage(ChatColor.GOLD + "Plot Denied!");
                    SQLGetter.setPlotStatus(PlotUtils.getId(p).toString(), "DENIED");
                }
                case RED_CONCRETE -> p.openInventory(GUIinit.invplotinfojudge);
            }

            e.setCancelled(true);
        }

    }
}