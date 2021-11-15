package me.koros3npai.kjudge.listeners;

import me.koros3npai.kjudge.KJudge;
import me.koros3npai.kjudge.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    private KJudge plugin;

    public MenuListener(KJudge plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }
            //Since we know our inventoryholder is a menu, get the Menu Object representing
            // the menu we clicked on
            Menu menu = (Menu) holder;
            //Call the handleMenu object which takes the event and processes it
            menu.handleMenu(e);
        }

    }

}
