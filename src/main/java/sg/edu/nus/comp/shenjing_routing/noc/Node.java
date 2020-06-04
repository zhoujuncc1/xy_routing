package sg.edu.nus.comp.shenjing_routing.noc;

import javax.naming.SizeLimitExceededException;

import sg.edu.nus.comp.shenjing_routing.utils.Pair;
import sg.edu.nus.comp.shenjing_routing.utils.Triple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node {
    public final Pair<Integer> index;
    public final Node[] neighbours;
    public final int buffer_size;

    List<NodeInstruction> instructions;
    public Queue<Message> buffer;

    public Node(int index_x, int index_y, int buffer_size) {
        this.buffer_size = buffer_size;
        buffer = new LinkedList<>();
        index = new Pair<>(index_x, index_y);
        neighbours = new Node[4];
        instructions = new ArrayList<>();
    }

    public boolean has_buffer() {
        return buffer.size() < buffer_size;
    }

    public void push(Message message) throws SizeLimitExceededException {
        if (buffer.size() == buffer_size)
            throw new SizeLimitExceededException("Buffer at (" + index.x + ", " + index.y + ") exceeds buffer size.");
        buffer.add(message);
    }

    public Message arrive(int time) {
        //Reached Dst
        Message msg = buffer.peek();
        if (msg!=null && msg.dst.equals(index)) {
            NodeInstruction instruction = new NodeInstruction(time, Constants.INSTR_RECV, Network.reverseDirection(msg.instructionTrace.get(msg.instructionTrace.size()-1).dst_direction), -1,msg.id);

            msg.nodeTrace.add(new Triple<>(index.x, index.y, time));
            msg.instructionTrace.add(instruction);
            msg.arrived=true;
            instructions.add(instruction);
            buffer.remove();
            return msg;
        }
        return null;
    }

    public int route(int time) throws SizeLimitExceededException {
        //No message
        if (buffer.isEmpty())
            return -1;


        Message msg = buffer.peek();
        //Route to Next
        boolean isOrigin = msg.src.equals(index);
        NodeInstruction instruction;
        int direction = -1;
        if (msg.dst.y < index.y && neighbours[Constants.WEST] != null && neighbours[Constants.WEST].has_buffer()) {
            direction = Constants.WEST;
        } else if (msg.dst.y > index.y && neighbours[Constants.EAST] != null && neighbours[Constants.EAST].has_buffer()) {
            direction = Constants.EAST;
        } else if (msg.dst.x < index.x && neighbours[Constants.NORTH] != null && neighbours[Constants.NORTH].has_buffer()) {
            direction = Constants.NORTH;
        } else if (msg.dst.x > index.x && neighbours[Constants.SOUTH] != null && neighbours[Constants.SOUTH].has_buffer()) {
            direction = Constants.SOUTH;
        }

        if (direction != -1) {
            if (isOrigin)
                instruction = new NodeInstruction(time, Constants.INSTR_SEND, -1, direction, msg.id);
            else
                instruction = new NodeInstruction(time, Constants.INSTR_BYPASS,
                        Network.reverseDirection(msg.instructionTrace.get(msg.instructionTrace.size()-1).dst_direction), direction, msg.id);
            msg.nodeTrace.add(new Triple<>(index.x, index.y, time));
            msg.instructionTrace.add(instruction);

            instructions.add(instruction);
            neighbours[direction].push(buffer.remove());
            return direction;
        } else {
            return -1;
        }

    }

    public void reset(){
        buffer.clear();
        instructions.clear();
    }
}
