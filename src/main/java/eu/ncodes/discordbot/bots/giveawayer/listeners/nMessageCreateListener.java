package eu.ncodes.discordbot.bots.giveawayer.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.giveawayer.Giveawayer;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.supporter.instances.nMessage;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.utils.FileUtils;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/*
    Class nMessageCreateListener

    Listener class for listening message
    and add points to users
*/

public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isBotUser()){

            if(((Giveawayer)DiscordUtils.bots.get("giveawayer")).getCache().IsMemberExists(event.getMessageAuthor().getId())){



            }

        }

    }

    /*
        Method onCreateMessage

        for create default message which you
        can start a new ticket

        Input variables:
        - ServerTextChannel channel : channel to send default message
    */
    private void onCreateMessage(ServerTextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#7900FF"))
                .setThumbnail("http://documents.ncodes.eu/support.jpg")
                .setTitle("Ticket System :tickets: ")
                .setDescription("Hi! :wave: \nDon't be shy and open new ticket by pressing a button below!")
                .addField("Rule #1", "Don't abuse ticket system")
                .addField("Rule #2", "After creating the ticket try to specify your problem")
                .addField("To add member into ticket", "n!s member add <user id>")
                .addField("To close ticket", "n!s close")
                .setFooter("Please be patient, @nSupport is still in progress!");

        new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(SelectMenu.create("ncodes-support-list", "Please select topic of ticket", 1, 1,
                                Arrays.asList(
                                        SelectMenuOption.create("Idea", "Selected topic : idea", "Select if you have some idea to discuss"),
                                        SelectMenuOption.create("Bug", "Selected topic : bug", "Select if you found some bug to discuss"),
                                        SelectMenuOption.create("Question", "Selected topic: question", "Select if you have some question to discuss")
                                ))),
                        ActionRow.of(Button.success("ncodes-support", "Start a ticket"))
                )
                .send(channel);

    }

    /*
        Method onMember

        for add or remove member in
        a current ticket

        Input variables:
        - MessageCreateEvent event : get information and later to update channel permissions
        - String subCommand : specify if it's add or remove
        - String targetId : targets ID
    */
    private void onMember(MessageCreateEvent event, String subCommand, String targetId){
        String channelName = event.getServer().get().getTextChannelById(event.getChannel().getId()).get().getName();
        int id = Integer.parseInt(channelName.substring(0, channelName.indexOf("-")));
        ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupportById(id).addMember(event.getMessageAuthor().getId(), event.getMessageAuthor().getName());
        User targetUser = null;
        try{
            targetUser = event.getServer().get().getMemberById(targetId).get();
        }
        catch(Exception ex){
            event.getMessage().reply("Target user with id '" + targetId + "' doesn't exists!");
            ex.printStackTrace();
            return;
        }

        if(subCommand.equals("add")){
            event.getChannel().asServerChannel().get().createUpdater().addPermissionOverwrite(targetUser, Permissions.fromBitmask(1024, 0)).update().join();
            event.getMessage().reply("You successfully added " + targetUser.getMentionTag());
        }
        else {
            event.getChannel().asServerChannel().get().createUpdater().addPermissionOverwrite(targetUser, Permissions.fromBitmask(0, 1024)).update().join();
            event.getMessage().reply("You successfully removed " + targetUser.getMentionTag());
        }
    }

    /*
        Method onClose

        for close current ticket, save into
        json format log and delete channel

        Input variables:
        - MessageCreateEvent event : get information and later delete channel
     */
    private void onClose(MessageCreateEvent event){
        event.getMessage().reply(event.getMessageAuthor().asUser().get().getMentionTag() + " you're closing this ticket!");
        String channelName = event.getServer().get().getTextChannelById(event.getChannel().getId()).get().getName();
        int id = Integer.parseInt(channelName.substring(0, channelName.indexOf("-")));

        nSupport support = ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupportById(id);
        event.getMessageAuthor().asUser().get().removeRole(event.getServer().get().getRoleById(DiscordDefaults.roleSupport).get());
        FileUtils.saveLog(support, error -> {

            if(error == null){

                event.getServer().get().getMemberById(support.getOwnerId()).ifPresent(user -> {
                    event.getServer().get().getRoleById(DiscordDefaults.roleSupport).ifPresent(role -> {
                        user.removeRole(role);
                    });
                });
                ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).removeSupport(support);
                int size =  ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupports().size();
                String status = size == 1 ? "1 ticket" : size + " tickets";
                DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getBot().updateActivity(ActivityType.WATCHING, status);
                event.getMessage().reply(event.getMessageAuthor().asUser().get().getMentionTag() + ", this channel will now close!");
                event.getServerTextChannel().get().delete();

            }

        });
    }

    /*
        Method onCache

        for send supporter bot cache as
        json format file

        Input variables:
        - TextChannel channel : get channel for sending cache
    */
    private void onCache(TextChannel channel){

        byte[] file = (new Gson().toJson(((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupports())).getBytes();

        new MessageBuilder()
                .setContent("Here is mine cache!")
                .addAttachment(file, "cache.json")
                .send(channel);

    }

    /*
        Method onTicketMessage

        for save message into cache includes
        download and save all attachments

        Input variables:
        - MessageCreateEvent event : get information
    */
    private void onTicketMessage(MessageCreateEvent event){
        String channelName = event.getServer().get().getTextChannelById(event.getChannel().getId()).get().getName();
        int id = Integer.parseInt(channelName.substring(0, channelName.indexOf("-")));
        nMessage message = new nMessage(event.getMessageId(), event.getMessageAuthor().getId(), event.getMessage().getCreationTimestamp(), event.getMessageContent());
        nSupport support = ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupportById(id);

        for (MessageAttachment attachment : event.getMessage().getAttachments()){

            String key, fileName;
            try {

                byte[] file = attachment.downloadAsByteArray().get();
                System.out.println(file.length);
                fileName = attachment.getFileName();
                key = fileName.substring(0, fileName.indexOf("."));
                String path = "./data/supporter/" + support.getCreated().getYear() + "/" + support.getCreated().getMonthValue() + "/" + support.getCreated().getDayOfMonth() + "/" + support.getId() + "-" + support.getOwnerName();

                new File(path).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(path + "/" + fileName)) {
                    fos.write(file);
                    fos.flush();
                }

                message.addAttachment(key, path + "/" + fileName);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        support.addMessage(message);

        if(!((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupportById(id).isMemberInSupport(event.getMessageAuthor().getId()))
            ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupportById(id).addMember(event.getMessageAuthor().getId(), event.getMessageAuthor().getName());
    }

}
