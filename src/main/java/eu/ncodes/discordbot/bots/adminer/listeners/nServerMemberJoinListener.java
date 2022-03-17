package eu.ncodes.discordbot.bots.adminer.listeners;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import java.util.ArrayList;

public class nServerMemberJoinListener implements ServerMemberJoinListener {

    private final String[] defaultRoles = new String[]{"612219084486082570",
                                                        "945424570066800661",
                                                        "945425891570356224",
                                                        "945428565674061844",
                                                        "945449588603621397"};

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {

        for(Role role : event.getServer().getRoles()){

            if(isInDefaultRoles(role.getIdAsString())){
                event.getUser().addRole(role);
            }

        }

    }

    private boolean isInDefaultRoles(String id){

        for(String item : defaultRoles){
            if(item.equals(id))
                return true;
        }
        return false;

    }

}
