package eu.ncodes.discordbot.bots.player.listeners;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import eu.ncodes.discordbot.bots.player.instances.LavaplayerAudioSource;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.List;

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

    private AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private AudioPlayer player = playerManager.createPlayer();
    private ServerVoiceChannel channel;

    private List<String> youtubeQueue = new ArrayList<>();
    private boolean playing = false;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String[] splitter = event.getMessage().getContent().split(" ");

        /*
            Checks if it's nCodes supporter command
        */
        if(splitter[0].equals("n!p")){

            /*
                Checks if it's youtube subcommand
            */
            if(splitter[1].equals("ytb") || splitter[1].equals("youtube")){
                LogSystem.log(DiscordUtils.supporter.getPrefix(), "youtube command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender is in voice channel room
                */
                event.getMessageAuthor().asUser().ifPresent(user -> {
                    user.getConnectedVoiceChannel(event.getServer().get()).ifPresent(serverVoiceChannel -> {
                        channel = serverVoiceChannel;
                        onPlayYoutube(splitter[2]);
                        LogSystem.log(DiscordUtils.supporter.getPrefix(), "end of youtube command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                    });
                });

            }

            else if(splitter[1].equals("skip")){

            }


        }
    }

    public void playNext(){

        if(youtubeQueue.size() > 0){
            loadItem(youtubeQueue.get(0));
            youtubeQueue.remove(0);
        }
        else{

        }

    }

    private void onPlayYoutube(String url){

        if(playing){
            youtubeQueue.add(url);
        }
        else{
            channel.connect().thenAccept(audioConnection -> {

                // Create a player manager

                playerManager.registerSourceManager(new YoutubeAudioSourceManager());

                // Create an audio source and add it to the audio connection's queue
                AudioSource source = new LavaplayerAudioSource(DiscordUtils.player.getBot(), player);
                audioConnection.setAudioSource(source);

                // You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,


            }).exceptionally(e -> {
                // Failed to connect to voice channel (no permissions?)
                e.printStackTrace();
                return null;
            });
        }

    }

    private void loadItem(String url){

        playerManager.loadItem(url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    player.playTrack(track);
                }
            }

            @Override
            public void noMatches() {
                // Notify the user that we've got nothing
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                // Notify the user that everything exploded
            }
        });

    }

}
