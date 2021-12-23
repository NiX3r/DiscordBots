package eu.ncodes.discordbot.bots.supporter.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public static void saveLog(nSupport support, Consumer<Exception> consumer){

        String path = "./data/supporter/" + support.getCreated().getYear() + "/" + support.getCreated().getMonthValue() + "/" + support.getCreated().getDayOfMonth() + "/" + support.getId() + "-" + support.getOwnerName();
        File file = new File(path);
        file.mkdirs();
        try {

            file = null;

            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    path + "/log.json"));
            f_writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(support));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(DiscordUtils.supporter.getPrefix(), "log '" + support.getId() + "-" + support.getOwnerName() + "' saved", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(null);

        } catch (IOException e) {
            LogSystem.log(DiscordUtils.supporter.getPrefix(), "can't save ticket log '" + support.getId() + "-" + support.getOwnerName() + "' saved. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(e);
        }

    }

    public static void saveCache(Consumer<Exception> consumer){

        String path = "./data/supporter";
        new File(path).mkdirs();

        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    path + "/cache.json"));
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(DiscordUtils.supporter.getSupports());
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            LogSystem.log("nSupporter", "cache saved", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(null);
        } catch (Exception e){
            LogSystem.log("nSupporter", "can't save cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(e);
        }

    }

    public static void loadCache(Consumer<ArrayList<nSupport>> consumer){

        String path = "./data/supporter/cache.json";

        try{
            String json = new String(Files.readAllBytes(Paths.get(path)));
            ArrayList<nSupport> sList = new ArrayList<nSupport>(Arrays.asList(new Gson().fromJson(json, nSupport[].class)));
            LogSystem.log("nSupporter", "cache loaded", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            consumer.accept(sList);
        } catch (Exception e){
            LogSystem.log("nSupporter", "can't load cache. Error: " + e.getMessage(), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            e.printStackTrace();
            consumer.accept(null);
        }

    }

}
