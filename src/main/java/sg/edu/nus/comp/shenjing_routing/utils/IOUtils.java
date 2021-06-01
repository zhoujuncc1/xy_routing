//Copyright (c) Dr Jun Zhou, National University of Singapore.
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

package sg.edu.nus.comp.shenjing_routing.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.shenjing_routing.noc.Message;

/**
 * Parse the messages from input files or strings. Each line contains a message
 * in the format of src_x,src_y,dst_x,dst_y. For example "1,2,2,3" represents
 * the message from (1,2) to (2,3). Messages are separated into groups by blank
 * lines. Those from one group are to be routed in parallel.
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
                assert indices.length >= 4;

                ArrayList<Pair<Integer>> dstList = new ArrayList<Pair<Integer>>();
                for (int i = 2; i < indices.length; i += 2)
                    dstList.add(new Pair<Integer>(Integer.parseInt(indices[i]), Integer.parseInt(indices[i + 1])));
                messages.add(new Message(id++, new Pair<>(Integer.parseInt(indices[0]), Integer.parseInt(indices[1])),
                        dstList));

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
