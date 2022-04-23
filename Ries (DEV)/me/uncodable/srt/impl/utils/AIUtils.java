/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.stream.Collectors;
import me.uncodable.srt.Ries;
import net.minecraft.client.Minecraft;

public class AIUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final String DATABASE_ROOT = "D:\\SRT AI Database\\";
    private static final String QUESTION = "QUESTION";
    private static final String STATEMENT = "STATEMENT";

    public static void logChat(String message) {
        FileOutputStream logger = null;
        try {
            String messageToWrite = "[" + Calendar.getInstance().getTime() + "] " + message + "\n";
            logger = new FileOutputStream(String.format("%sChat\\Chat.txt", DATABASE_ROOT), true);
            logger.write(messageToWrite.getBytes(StandardCharsets.UTF_8));
            logger.flush();
        }
        catch (FileNotFoundException fileNotFoundException) {
            Ries.INSTANCE.msg("Chat log file not found!");
            try {
                assert (logger != null);
                logger.close();
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static String readAndGetResponse(String readMessage) {
        String[] splitReadMessage = readMessage.split(" ");
        String bestMatchResponse = "I am uncertain on how to respond to that. Could you try rephrasing it?";
        String category = "";
        int bestMatchCounter = 0;
        int matchCounter = 0;
        try {
            BufferedReader wordReader = new BufferedReader(new FileReader(String.format("%sWords\\Question Signals.txt", DATABASE_ROOT)));
            if (readMessage.contains("?")) {
                category = QUESTION;
            } else {
                block6: for (String read : wordReader.lines().collect(Collectors.toList())) {
                    for (String s : splitReadMessage) {
                        if (!s.equalsIgnoreCase(read)) continue;
                        category = QUESTION;
                        continue block6;
                    }
                }
            }
            if (!category.equals(QUESTION)) {
                category = STATEMENT;
            }
            wordReader.close();
        }
        catch (FileNotFoundException e) {
            Ries.INSTANCE.msg("Word database not found!");
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            BufferedReader sentenceReader = new BufferedReader(new FileReader(String.format("%sSentences\\Sentences.txt", DATABASE_ROOT)));
            for (String read : sentenceReader.lines().collect(Collectors.toList())) {
                String[] categoryCompare = read.split(":");
                if (category.equals(categoryCompare[categoryCompare.length - 1])) {
                    String[] sentenceCompare = read.replace(String.format(":%s", category), "").split(" ");
                    for (int i = 0; i < Math.max(sentenceCompare.length, splitReadMessage.length); ++i) {
                        if (i > sentenceCompare.length - 1 || i > splitReadMessage.length - 1) continue;
                        if (sentenceCompare[i].equalsIgnoreCase(splitReadMessage[i])) {
                            AIUtils.MC.thePlayer.sendChatMessage("[DEBUG] matchCounter:" + ++matchCounter);
                        }
                        if (i + 1 >= splitReadMessage.length - 1 || !sentenceCompare[i].equalsIgnoreCase(splitReadMessage[i + 1])) continue;
                        AIUtils.MC.thePlayer.sendChatMessage("[DEBUG] matchCounter:" + ++matchCounter);
                    }
                    if (matchCounter > bestMatchCounter) {
                        bestMatchCounter = matchCounter;
                        AIUtils.MC.thePlayer.sendChatMessage("[DEBUG] bestMatchCounter: " + bestMatchCounter);
                    }
                }
                matchCounter = 0;
            }
            sentenceReader.close();
        }
        catch (FileNotFoundException e) {
            Ries.INSTANCE.msg("Sentence database not found!");
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return bestMatchResponse;
    }

    public static void respond(String message) {
        Ries.INSTANCE.console("MY NAME: " + AIUtils.MC.thePlayer.getGameProfile().getName());
        String ping = String.format("@%s", AIUtils.MC.thePlayer.getGameProfile().getName());
        if (message.contains(ping)) {
            AIUtils.MC.thePlayer.sendChatMessage(AIUtils.readAndGetResponse(message.split(ping + " ")[1]));
        }
    }
}

