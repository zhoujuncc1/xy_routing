package sg.edu.nus.comp.shenjing_routing.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.shenjing_routing.noc.Message;

/**
 * Parse the messages from input files or strings.
 * Each line contains a message in the format of src_x,src_y,dst_x,dst_y.
 * For example "1,2,2,3" represents the message from (1,2) to (2,3).
 * Messages are separated into groups by blank lines. Those from one group are to be routed in parallel.
 */
public class IOUtils {
    public static List<List<Message>> parseMessages(List<String> input) {
        List<List<Message>> messageGroups = new ArrayList<List<Message>>();
        List<Message> messages = new ArrayList<>();
        messageGroups.add(messages);
        int id = 0;
        for (String s : input) {
            if (s.isEmpty()) {
                messages = new ArrayList<>();
                messageGroups.add(messages);
            } else {
                String[] indices = s.split(",");
                assert indices.length == 4;
                messages.add(new Message(id++, new Pair<>(Integer.parseInt(indices[0]), Integer.parseInt(indices[1])),
                        new Pair<>(Integer.parseInt(indices[2]), Integer.parseInt(indices[3]))));
            }
        }
        return messageGroups;
    }

    public static List<List<Message>> parseMessages(String filename) throws IOException {
        return parseMessages(Files.readAllLines(Paths.get(filename)));

    }

    public static List<List<Message>> encodeInstructions(List<String> input) {
        List<List<Message>> messageGroups = new ArrayList<List<Message>>();
        List<Message> messages = new ArrayList<>();
        messageGroups.add(messages);
        int id = 0;
        for (String s : input) {
            if (s.isEmpty()) {
                messages = new ArrayList<>();
                messageGroups.add(messages);
            } else {
                String[] indices = s.split(",");
                assert indices.length == 4;
                messages.add(new Message(id++, new Pair<>(Integer.parseInt(indices[0]), Integer.parseInt(indices[1])),
                        new Pair<>(Integer.parseInt(indices[2]), Integer.parseInt(indices[3]))));
            }
        }
        return messageGroups;
    }
}
