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
