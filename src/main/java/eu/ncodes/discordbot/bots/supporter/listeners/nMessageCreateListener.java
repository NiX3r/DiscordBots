package eu.ncodes.discordbot.bots.supporter.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.supporter.instances.nMessage;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.utils.FileLog;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
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

    Listener class for listening message commands
    and messages in ticket channels to save them

    Includes:
    - message : create default message for creating new tickets
    - member : add or remove members into / from ticket
    - close : close and save current ticket
    - cache : send file which contains cache
*/

public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String[] splitter = event.getMessage().getContent().split(" ");

        /*
            Checks if it's nCodes supporter command
        */
        if(splitter[0].equals("n!s")){

            /*
                Checks if it's message subcommand
            */
            if(splitter[1].equals("msg") || splitter[1].equals("message")){
                /*
                    Checks if command contains 1 mention channel
                */
                if(event.getMessage().getMentionedChannels().size() == 1){
                    onCreateMessage(event.getMessage().getMentionedChannels().get(0));
                }
                event.getMessage().reply("You have to mention 1 text channel!");
            }

            /*
                Checks if it's member subcommand
            */
            else if(splitter[1].equals("member") && (splitter[2].equals("add") || splitter[2].equals("remove")) && splitter.length == 4){
                /*
                    Checks if sender has Support role or has AT member role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleSupport) ||
                        DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAtMember)){

                    onMember(event, splitter[2], splitter[3]);

                }
            }

            /*
                Checks if it's close subcommand
            */
            else if(splitter[1].equals("close")){
                /*
                    Checks if sender has Support role or has AT member role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleSupport) ||
                        DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAtMember)){
                    onClose(event);
                }
            }

            /*
                Checks if it's cache subcommand
            */
            else if(splitter[1].equals("cache")){
                /*
                    Checks if sender has Admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    onCache(event.getMessage().getChannel());
                }
            }

        }
        /*
            Checks if message isn't supporter command
        */
        else{
            event.getChannel().asServerTextChannel().get().getCategory().ifPresent(channelCategory -> {

                if(channelCategory.getIdAsString().equals(DiscordDefaults.categorySupport) &&
                        !event.getMessageAuthor().isBotUser()){

                    onTicketMessage(event);

                }

            });
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
                .addField("Rule #2", "After create ticket try to most specify your problem")
                .addField("To add member into ticket", "n!s member add <user id>")
                .addField("To close ticket", "n!s close")
                .setFooter("Discord is in beta version!");

        new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(SelectMenu.create("ncodes-support-list", "Please select topic of ticket", 1, 1,
                                Arrays.asList(
                                        SelectMenuOption.create("Idea", "Selected topic : idea", "Select if you have some idea to discuss"),
                                        SelectMenuOption.create("Bug", "Selected topic : bug", "Select if you found some bug to discuss")
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
        DiscordUtils.supporter.getSupportById(id).addMember(event.getMessageAuthor().getId(), event.getMessageAuthor().getName());
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

        nSupport support = DiscordUtils.supporter.getSupportById(id);
        event.getMessageAuthor().asUser().get().removeRole(event.getServer().get().getRoleById(DiscordDefaults.roleSupport).get());
        FileLog.saveLog(support, error -> {

            if(error == null){

                DiscordUtils.supporter.removeSupport(support);
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

        byte[] file = (new Gson().toJson(DiscordUtils.supporter.getSupports())).getBytes();

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
        nSupport support = DiscordUtils.supporter.getSupportById(id);

        for (MessageAttachment attachment : event.getMessage().getAttachments()){

            String key, fileName;
            try {

                byte[] file = attachment.downloadAsByteArray().get();
                System.out.println(file.length);
                fileName = attachment.getFileName();
                key = fileName.substring(0, fileName.indexOf("."));
                String path = "./logs/" + support.getCreated().getYear() + "/" + support.getCreated().getMonthValue() + "/" + support.getCreated().getDayOfMonth() + "/" + support.getId() + "-" + support.getOwnerName();

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

        if(!DiscordUtils.supporter.getSupportById(id).isMemberInSupport(event.getMessageAuthor().getId()))
            DiscordUtils.supporter.getSupportById(id).addMember(event.getMessageAuthor().getId(), event.getMessageAuthor().getName());
    }

}
