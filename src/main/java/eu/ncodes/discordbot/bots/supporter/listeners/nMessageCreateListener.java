package eu.ncodes.discordbot.bots.supporter.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.supporter.instances.nMessage;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.utils.FileLog;
import eu.ncodes.discordbot.utils.DiscordDefaultIDs;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String[] splitter = event.getMessage().getContent().split(" ");

        if(splitter[0].equals("n!s")){

            if(splitter[1].equals("msg") || splitter[1].equals("message")){
                if(event.getMessage().getMentionedChannels().size() == 1){
                    onCreateMessage(event.getMessage().getMentionedChannels().get(0));
                    return;
                }
                event.getMessage().reply("You have to mention 1 text channel!");
            }

            else if(splitter[1].equals("member") && (splitter[2].equals("add") || splitter[2].equals("remove")) && splitter.length == 4){
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaultIDs.roleSupport) ||
                        DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaultIDs.roleAtMember)){

                    String channelName = event.getServer().get().getTextChannelById(event.getChannel().getId()).get().getName();
                    int id = Integer.parseInt(channelName.substring(0, channelName.indexOf("-")));
                    DiscordUtils.supporter.getSupportById(id).addMember(event.getMessageAuthor().getId(), event.getMessageAuthor().getName());
                    User targetUser = null;
                    try{
                        targetUser = event.getServer().get().getMemberById(splitter[3]).get();
                    }
                    catch(Exception ex){
                        event.getMessage().reply("Target user with id '" + splitter[3] + "' doesn't exists!");
                        ex.printStackTrace();
                        return;
                    }

                    if(splitter[2].equals("add")){
                        event.getChannel().asServerChannel().get().createUpdater().addPermissionOverwrite(targetUser, Permissions.fromBitmask(1024, 0)).update().join();
                        event.getMessage().reply("You successfully added " + targetUser.getMentionTag());
                    }
                    else {
                        event.getChannel().asServerChannel().get().createUpdater().addPermissionOverwrite(targetUser, Permissions.fromBitmask(0, 1024)).update().join();
                        event.getMessage().reply("You successfully removed " + targetUser.getMentionTag());
                    }

                }
            }

            else if(splitter[1].equals("close")){
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaultIDs.roleSupport) ||
                        DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaultIDs.roleAtMember)){

                    event.getMessage().reply(event.getMessageAuthor().asUser().get().getMentionTag() + " you're closing this ticket!");
                    String channelName = event.getServer().get().getTextChannelById(event.getChannel().getId()).get().getName();
                    int id = Integer.parseInt(channelName.substring(0, channelName.indexOf("-")));

                    nSupport support = DiscordUtils.supporter.getSupportById(id);
                    FileLog.saveLog(support, error -> {

                        if(error == null){

                            DiscordUtils.supporter.removeSupport(support);
                            event.getMessage().reply(event.getMessageAuthor().asUser().get().getMentionTag() + ", this channel will now close!");
                            event.getServerTextChannel().get().delete();

                        }

                    });



                }
            }

            else if(splitter[1].equals("cache")){
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaultIDs.roleAdmin)){
                    event.getMessage().reply("```json\n" + new Gson().toJson(DiscordUtils.supporter.getSupports()) + "\n```");
                }
            }

        }

        else if(event.getChannel().asServerTextChannel().get().getCategory().get().getIdAsString().equals(DiscordDefaultIDs.categorySupport) &&
                !event.getMessageAuthor().isBotUser()){

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
                    String path = "./logs/" + support.getCreated().getYear() + "/" + support.getCreated().getMonthValue() + "/" + support.getCreated().getDayOfMonth() + "/" + support.getId() + "-" + support.getOwnerName() + "/" + fileName;

                    try (FileOutputStream fos = new FileOutputStream(path)) {
                        fos.write(file);
                        fos.flush();
                    }

                    message.addAttachment(key, path);

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

    private void onCreateMessage(ServerTextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#7900FF"))
                .setThumbnail("http://documents.ncodes.eu/support.jpg")
                .setTitle("Ticket System :tickets: ")
                .setDescription("Hi! :wave: \nDon't be shy and open new ticket by pressing a button below!")
                .addField("Rule #1", "Don't abuse ticket system")
                .addField("Rule #2", "After create ticket try to most specify your problem")
                .addField("To add member into ticket", "n!s member add <user id>")
                .addField("To close ticket", "n!s close");

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

}
