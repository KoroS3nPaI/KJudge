package me.koros3npai.kjudge.utils;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.api.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;

import org.bukkit.entity.Player;

public class PlotUtils extends PlotAPI{

    public PlotUtils(){
    }

    public static PlotId getId(Player player){
        PlotPlayer p = BukkitUtil.getPlayer(player); //BukkitUtil.adapt(player);
        Plot plot = p.getCurrentPlot();

        if(plot == null || !plot.hasOwner()){
            return null;
        } else {
            return plot.getId();
        }
    }

    public static String printId(Player player){
        PlotPlayer p = BukkitUtil.getPlayer(player); //BukkitUtil.adapt(player);
        Plot plot = p.getCurrentPlot();

        if(plot == null || !plot.hasOwner()){
            return "You must be at your plot";
        } else {

            if(plot.isOwner(p.getUUID())){
                return "Plot ID: " + getId(player).toString();
            } else {
                return "You must be at your plot";
            }

        }

    }

    public static boolean plotCheck(Player player){
        PlotPlayer p = BukkitUtil.getPlayer(player); //BukkitUtil.adapt(player);
        Plot plot = p.getCurrentPlot();

        if(plot == null || !plot.hasOwner()){
            return false;
        } else {

            if(plot.isOwner(p.getUUID())){
                return true;
            } else {
                return false;
            }

        }
    }

    public static boolean plotCheckNull(Player player) {
        PlotPlayer p = BukkitUtil.getPlayer(player); //BukkitUtil.adapt(player);
        Plot plot = p.getCurrentPlot();

        if (plot == null || !plot.hasOwner()) return false;
        else return true;
    }
}
