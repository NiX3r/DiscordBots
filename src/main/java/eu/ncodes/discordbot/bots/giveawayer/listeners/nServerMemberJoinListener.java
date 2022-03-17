package eu.ncodes.discordbot.bots.giveawayer.listeners;

import eu.ncodes.discordbot.bots.giveawayer.Giveawayer;
import eu.ncodes.discordbot.bots.giveawayer.instances.Invite;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class nServerMemberJoinListener implements ServerMemberJoinListener {

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

        /*for(Invite invite : ((Giveawayer) DiscordUtils.bots.get("giveawayer")).getCache().invites){
            ((Giveawayer) DiscordUtils.bots.get("giveawayer")).getBot().getInviteByCode(invite.getCode()).get()
                    .getApproximateMemberCount()
        }*/

    }

}
