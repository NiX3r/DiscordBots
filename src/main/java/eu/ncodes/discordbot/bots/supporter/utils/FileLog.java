package eu.ncodes.discordbot.bots.supporter.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class FileLog {

    public static void saveLog(nSupport support, Consumer<Exception> consumer){

        String path = "./logs/" + support.getCreated().getYear() + "/" + support.getCreated().getMonthValue() + "/" + support.getCreated().getDayOfMonth() + "/" + support.getId() + "-" + support.getOwnerName();
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
            consumer.accept(e);
            e.printStackTrace();
        }


    }

}
