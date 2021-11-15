package me.koros3npai.kjudge.utils;

import me.koros3npai.kjudge.KJudge;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetGroupLP {
    private final KJudge plugin;
    private final LuckPerms luckPerms;

    public GetGroupLP(KJudge plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }


    public String getPlayerGroup(OfflinePlayer player){

        // Get a Bukkit player adapter.
        PlayerAdapter<Player> playerAdapter = this.luckPerms.getPlayerAdapter(Player.class);

        // Get a LuckPerms user for the player.
        User user = playerAdapter.getUser((Player) player);

        // Get all of the groups they inherit from on the current server.
        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions((Player) player));

        // Convert to a comma separated string (e.g. "admin, mod, default")
        String groupsString = groups.stream().map(Group::getName).collect(Collectors.joining(", "));

        List<String> gl = new ArrayList<>(Arrays.asList(groupsString.split(", ")));
        List<String> finalgroup = new ArrayList<>();

        for(int i = 0; i < gl.size(); i++){
            for(int j = 0; j < creativeGroups().size(); j++){
                if(gl.get(i).contains(creativeGroups().get(j))){
                    finalgroup.add(gl.get(i));
                }
            }
        }

        String group = String.join(", ", finalgroup);

        return group;
    }

    public List<String> creativeGroups(){
        List<String> cgroup = new ArrayList<>();

        cgroup.add("novice");
        cgroup.add("disciple");
        cgroup.add("mentor");
        cgroup.add("guru");
        cgroup.add("expert");
        cgroup.add("master");

        return cgroup;
    }
}
