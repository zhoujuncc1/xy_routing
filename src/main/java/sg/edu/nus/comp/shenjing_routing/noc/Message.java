package sg.edu.nus.comp.shenjing_routing.noc;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.shenjing_routing.utils.Pair;
import sg.edu.nus.comp.shenjing_routing.utils.Triple;

public class Message {
    int id;
    Pair<Integer> src;
    Pair<Integer> dst;

    List<NodeInstruction> instructionTrace;
    List<Triple<Integer>> nodeTrace;
    boolean arrived;

    public Message(int id, Pair<Integer> src, Pair<Integer> dst) {
        this.id = id;
        this.src = src;
        this.dst = dst;
        instructionTrace=new ArrayList<>();
        nodeTrace=new ArrayList<>();
        arrived=false;
    }

    @Override
    public String toString(){
        return String.format("(%d, %d) -> (%d, %d)",src.x, src.y, dst.x, dst.y);
    }

    public String printNodeTrace(){
        StringBuilder stringBuilder=new StringBuilder();
        for(Triple<Integer> node : nodeTrace) {
            stringBuilder.append(String.format("(%d, %d)@%d -> ", node.x, node.y, node.z));
        }
        if(stringBuilder.length()>=3)
            stringBuilder.delete(stringBuilder.length()-3, stringBuilder.length());
        return stringBuilder.toString();
    }
}
