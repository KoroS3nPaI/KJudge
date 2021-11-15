package me.koros3npai.kjudge.sql;

import me.koros3npai.kjudge.KJudge;

import me.koros3npai.kjudge.classes.PlotSubmit;
import me.koros3npai.kjudge.listeners.GUIlistener;
import org.bukkit.entity.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLGetter {

    private static KJudge plugin;
    public SQLGetter(KJudge plugin){
        SQLGetter.plugin = plugin;
    }

    private static PlotSubmit pluginp;
    public SQLGetter(PlotSubmit  pluginp){
        SQLGetter.pluginp = pluginp;
    }

    private static GUIlistener pluging;
    public SQLGetter(GUIlistener  pluging){
        SQLGetter.pluging = pluging;
    }


    public void createTable(){
        PreparedStatement ps;
        try{
            ps = plugin.SQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS plotsubmit "
                            + "(NAME VARCHAR(100),UUID VARCHAR(100),PLOTID VARCHAR(100),PLOTTITLE VARCHAR(100),PLOTLORE VARCHAR(6000),STATUS VARCHAR(50),COMMENT VARCHAR(6000),PRIMARY KEY(PLOTID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createPlot(String id, Player player, UUID uuid, String title, String lore, String status, String comment){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, id);
            ResultSet results = ps.executeQuery();
            results.next();
            if (!exists(UUID.nameUUIDFromBytes(id.getBytes()))){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement(
                        "INSERT IGNORE INTO plotsubmit " + "(NAME,UUID,PLOTID,PLOTTITLE,PLOTLORE,STATUS,COMMENT) VALUES (?,?,?,?,?,?,?)");
                ps2.setString(1,player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setString(3, id);
                ps2.setString(4, title);
                ps2.setString(5, lore);
                ps2.setString(6, status);
                ps2.setString(7, comment);
                ps2.executeUpdate();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean exists(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM plotsubmit WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            return results.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPlayerName(String id){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT NAME FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            String name;
            if(rs.next()){
                name = rs.getString("NAME");
                return name;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public static void addPlotID(UUID uuid, String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET PLOTID=? WHERE UUID=?");
            ps.setString(1, plotID);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getPlotIDPlayer(Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTID FROM plotsubmit WHERE NAME=?");
            ps.setString(1, p.getName());
            ResultSet rs = ps.executeQuery();
            String id;
            if(rs.next()){
                id = rs.getString("PLOTID");
                return id;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public static List<String> getPlotIDSubmitted(){
        List<String> id = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTID FROM plotsubmit WHERE STATUS=?");
            ps.setString(1, "PENDING");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id.add(rs.getString("PLOTID"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public static List<String> getPlayerPlotIDSubmitted(Player p){
        List<String> id = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTID FROM plotsubmit WHERE STATUS=? AND NAME=?");
            ps.setString(1, "PENDING");
            ps.setString(2, p.getName());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id.add(rs.getString("PLOTID"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public static void addPlotTitle(String plotID, String plotTitle){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET PLOTTITLE=? WHERE PLOTID=?");
            ps.setString(1, plotTitle);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getPlotTitle(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTTITLE FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            String title;
            if(rs.next()){
                title = rs.getString("PLOTTITLE");
                return title;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public static void addPlotLore(String plotID, String plotLore){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET PLOTLORE=? WHERE PLOTID=?");
            ps.setString(1, plotLore);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getPlotLore(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTLORE FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            String lore;
            if(rs.next()){
                lore = rs.getString("PLOTLORE");
                return lore;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public static void setPlotStatus(String plotID, String status){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET STATUS=? WHERE PLOTID=?");
            ps.setString(1, status);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPlotStatus(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT STATUS FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            String status;
            if(rs.next()){
                status = rs.getString("STATUS");
                return status;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "NOT SUBMITTED";
    }

    public static void addPlotComment(String plotID, String plotComment){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET COMMENT=? WHERE PLOTID=?");
            ps.setString(1, plotComment);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getPlotComment(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT COMMENT FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            String comment;
            if(rs.next()){
                comment = rs.getString("COMMENT");
                return comment;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

}
