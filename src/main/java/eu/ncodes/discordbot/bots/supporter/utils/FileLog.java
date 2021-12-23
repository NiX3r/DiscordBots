package eu.ncodes.discordbot.bots.supporter.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.utils.DiscordUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileLog {

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

            consumer.accept(null);

        } catch (IOException e) {
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
            System.out.println(json);
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            consumer.accept(null);
        } catch (Exception e){
            e.printStackTrace();
            consumer.accept(e);
        }

    }

    public static void loadCache(Consumer<ArrayList<nSupport>> consumer){

        String path = "./data/supporter/cache.json";

        try{
            String json = new String(Files.readAllBytes(Paths.get(path)));
            ArrayList<nSupport> sList = new ArrayList<nSupport>(Arrays.asList(new Gson().fromJson(json, nSupport[].class)));
            consumer.accept(sList);
        } catch (Exception e){
            e.printStackTrace();
            consumer.accept(null);
        }

    }

}
