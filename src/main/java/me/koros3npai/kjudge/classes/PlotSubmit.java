package me.koros3npai.kjudge.classes;

import me.koros3npai.kjudge.sql.SQLGetter;
import me.koros3npai.kjudge.utils.PlotUtils;
import org.bukkit.entity.Player;
import java.util.UUID;

public class PlotSubmit {
    public static SQLGetter data;

    public PlotSubmit(){
        this.data = new SQLGetter(this);
    }

    public static void setPlotTable(Player p){
        if(PlotUtils.plotCheck(p)) {
            String id;
            id = PlotUtils.getId(p).toString();
            data.createPlot(id, p, UUID.nameUUIDFromBytes(id.getBytes()), "EMPTY", "EMPTY", "NOT SUBMITTED", "EMPTY");
        }
    }


}
