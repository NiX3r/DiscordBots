package eu.ncodes.discordbot.bots.player.instances;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.audio.AudioSourceBase;
import org.javacord.api.audio.AudioTransformer;
import org.javacord.api.listener.ObjectAttachableListener;
import org.javacord.api.listener.audio.AudioSourceAttachableListener;
import org.javacord.api.listener.audio.AudioSourceFinishedListener;
import org.javacord.api.util.event.ListenerManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LavaplayerAudioSource extends AudioSourceBase {

    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    /**
     * Creates a new lavaplayer audio source.
     *
     * @param api A discord api instance.
     * @param audioPlayer An audio player from Lavaplayer.
     */
    public LavaplayerAudioSource(DiscordApi api, AudioPlayer audioPlayer) {
        super(api);
        this.audioPlayer = audioPlayer;

        this.addAudioSourceFinishedListener(listener -> {

        });

    }

    @Override
    public byte[] getNextFrame() {
        if (lastFrame == null) {
            return null;
        }
        return applyTransformers(lastFrame.getData());
    }

    @Override
    public boolean hasFinished() {
        return false;
    }

    @Override
    public boolean hasNextFrame() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public AudioSource copy() {
        return new LavaplayerAudioSource(getApi(), audioPlayer);
    }

}
