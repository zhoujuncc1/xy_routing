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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulator {
    Network network;
    public Simulator(int x_size, int y_size, int bufferSize, int timeOut){
        network = new Network(x_size, y_size, bufferSize, timeOut);
    }

    public String routeToString(String messageListString) throws SizeLimitExceededException {
        String[] stringList = messageListString.split("\n");
        List<List<Message>> messageGroups = IOUtils.parseMessages(Arrays.asList(stringList));
        StringBuilder stringBuilder = new StringBuilder();
        for(List<Message> msgs : messageGroups){
            network.route(msgs);
            List<NodeInstruction>[][] instrArr = network.getInstructions();
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

    public List<List<NodeInstruction>[][]> routeToInstructions(String messageListString) throws SizeLimitExceededException {
        String[] stringList = messageListString.split("\n");
        List<List<Message>> messageGroups = IOUtils.parseMessages(Arrays.asList(stringList));
        List<List<NodeInstruction>[][]> instructions = new ArrayList<>();
        for(List<Message> msgs : messageGroups){
            network.route(msgs);
            instructions.add(network.getInstructions());
        }
        return instructions;
    }
}
