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

package sg.edu.nus.comp.shenjing_routing.noc;

import javax.naming.SizeLimitExceededException;

import sg.edu.nus.comp.shenjing_routing.utils.IOUtils;
import sg.edu.nus.comp.shenjing_routing.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulator {
    Network network;
    public List<NodeInstruction>[][] totalInstructions;
    int x_size, y_size;
    public Simulator(int x_size, int y_size, int bufferSize, int timeOut){
        this.x_size=x_size;
        this.y_size=y_size;
        network = new Network(x_size, y_size, bufferSize, timeOut);
        totalInstructions = new List[x_size][y_size];
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                totalInstructions[i][j] = new ArrayList<NodeInstruction>();
            }
        }
    }

    public String routeToString(String messageListString, int cycle_offset) throws SizeLimitExceededException {
        String[] stringList = messageListString.split("\n");
        List<List<Message>> messageGroups = IOUtils.parseMessages(Arrays.asList(stringList));
        StringBuilder stringBuilder = new StringBuilder();
        for(List<Message> msgs : messageGroups){
            network.route(msgs, cycle_offset);
            List<NodeInstruction>[][] instrArr = network.getInstructions();
            addToHistory(instrArr);
            for (int i = 0; i < network.x_size; i++) {
                for (int j = 0; j < network.y_size; j++) {
                    for(NodeInstruction instr : instrArr[i][j])
                        stringBuilder.append(instr.encode()).append("\n");
                    stringBuilder.append("\n");
                }
            }

        }
        return stringBuilder.toString();
    }

    public List<List<NodeInstruction>[][]> routeToInstructions(String messageListString, int cycle_offset) throws SizeLimitExceededException {
        String[] stringList = messageListString.split("\n");
        List<List<Message>> messageGroups = IOUtils.parseMessages(Arrays.asList(stringList));
        List<List<NodeInstruction>[][]> instructions = new ArrayList<>();
        for(List<Message> msgs : messageGroups){
            network.route(msgs, cycle_offset);
            List<NodeInstruction>[][] instr = network.getInstructions();
            instructions.add(instr);
            addToHistory(instr);
        }
        return instructions;
    }



    public void clearHistory(){
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                totalInstructions[i][j].clear();
            }
        }
    }
    public void addToHistory(List<NodeInstruction>[][] instr){
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                totalInstructions[i][j].addAll(instr[i][j]);
            }
        }
    }
}
