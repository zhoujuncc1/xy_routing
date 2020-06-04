package sg.edu.nus.comp.shenjing_routing.example;

import javax.naming.SizeLimitExceededException;

import sg.edu.nus.comp.shenjing_routing.noc.Message;
import sg.edu.nus.comp.shenjing_routing.noc.Network;
import sg.edu.nus.comp.shenjing_routing.noc.NodeInstruction;
import sg.edu.nus.comp.shenjing_routing.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class MNIST_MLP {
    public static void main(String[] args) throws IOException, SizeLimitExceededException {
        String filename = "resources/mnist_mlp_spike_input.txt";
        Network network = new Network(4, 3);
        List<List<Message>> messageGroups = IOUtils.parseMessages(filename);
        for(List<Message> msgs : messageGroups){
            network.route(msgs);
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
