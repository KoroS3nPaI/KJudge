package me.koros3npai.kjudge.menu;

import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class SubmittedMenu extends PaginatedMenu {

    public SubmittedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Submitted Plots";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        List<String> plots = SQLGetter.getPlotIDSubmitted();
        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            p.performCommand("p v " + String.join("", e.getCurrentItem().getItemMeta().getLore().get(0)));
        }else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {

            //close inventory
            p.closeInventory();

        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= plots.size())){
                    page = page + 1;
                    super.open();
                }else{
                    p.sendMessage(ChatColor.GRAY + "You are on the last page.");
                }
            }
        }

    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        List<String> plots = SQLGetter.getPlotIDSubmitted();
        if (plots != null && !plots.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if(index >= plots.size()) break;
                if(plots.get(index) != null) {
                    //Getting all submitted plots
                    OfflinePlayer p = Utils.getPlayerByID(plots.get(index));
                    inventory.addItem(Utils.getHead(p, plots.get(index)));
                }
            }
        }
    }
}
