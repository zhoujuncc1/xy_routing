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

package sg.edu.nus.comp.xy_routing.example;

import javax.naming.SizeLimitExceededException;

import sg.edu.nus.comp.xy_routing.noc.Message;
import sg.edu.nus.comp.xy_routing.noc.Network;
import sg.edu.nus.comp.xy_routing.noc.NodeInstruction;
import sg.edu.nus.comp.xy_routing.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class MNIST_MLP {
    public static void main(String[] args) throws IOException, SizeLimitExceededException {
        String filename = "resources/mnist_mlp_spike_input.txt";
        Network network = new Network(4, 3);
        List<List<Message>> messageGroups = IOUtils.parseMessages(filename);
        for(List<Message> msgs : messageGroups){
            network.route(msgs,0);
            for(Message msg: msgs){
                System.out.print(msg.toString()+": ");
                System.out.println(msg.printNodeTrace());
            }
            System.out.println();
            List<NodeInstruction>[][] instrArr = network.getInstructions();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < network.x_size; i++) {
                for (int j = 0; j < network.y_size; j++) {
                    for(NodeInstruction instr : instrArr[i][j])
                        stringBuilder.append(String.format("(%d, %d): ", i,j)).append(instr.encode()).append("\n");
                    stringBuilder.append("\n");
                }
            }
            System.out.println(stringBuilder.toString());
        }
    }
}
