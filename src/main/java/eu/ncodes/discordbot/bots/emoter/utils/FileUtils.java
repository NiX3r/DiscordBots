package eu.ncodes.discordbot.bots.emoter.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ncodes.discordbot.bots.emoter.instances.nReaction;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileUtils {

    public static void saveCache(Consumer<Exception> consumer){

        String path = "./data/emoter";
        new File(path).mkdirs();

        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    path + "/cache.json"));
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(DiscordUtils.emoter.getReactionList());
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            LogSystem.log("nEmoter", "cache saved", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(null);
        } catch (Exception e){
            LogSystem.log("nEmoter", "can't save cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(e);
        }

    }

    public static void loadCache(Consumer<ArrayList<nReaction>> consumer){

        String path = "./data/emoter/cache.json";

        try{
            String json = new String(Files.readAllBytes(Paths.get(path)));
            ArrayList<nReaction> sList = new ArrayList<nReaction>(Arrays.asList(new Gson().fromJson(json, nReaction[].class)));
            LogSystem.log("nEmoter", "cache loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(sList);
        } catch (Exception e){
            LogSystem.log("nEmoter", "can't load cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(null);
        }

    }

}
