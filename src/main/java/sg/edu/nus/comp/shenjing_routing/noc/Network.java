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

import sg.edu.nus.comp.shenjing_routing.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class Network {

    public static int reverseDirection(int direction) {
        return 3 - direction;
    }

    /**
     * @param direction The router direction to encode to string
     * @param single    single letter (NSWE) or full word (NORTH, SOUTH, WEST, EAST)
     * @return Encode the router instruction direction to string
     */
    public static String directionToString(int direction, boolean single) {
        if(direction == -1)
            return "0";
        else
            return single ? Constants.DIRECTION_LETTER[direction] : Constants.DIRECTION_WORD[direction];
    }

    public final Node[][] nodes;

    private boolean[][] moved;
    private List<Message> finished;

    public final int x_size;
    public final int y_size;
    public final int timeout;

    public Network(int x_size, int y_size, int buffer_size, int timeout) {
        this.x_size = x_size;
        this.y_size = y_size;
        this.timeout = timeout;

        nodes = new Node[x_size][y_size];
        moved = new boolean[x_size][y_size];
        finished = new ArrayList<>();

        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                nodes[i][j] = new Node(i, j, buffer_size);
            }
        }

        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                if (i > 0)
                    nodes[i][j].neighbours[Constants.NORTH] = nodes[i - 1][j];
                if (i < x_size - 1)
                    nodes[i][j].neighbours[Constants.SOUTH] = nodes[i + 1][j];
                if (j > 0)
                    nodes[i][j].neighbours[Constants.WEST] = nodes[i][j - 1];
                if (j < y_size - 1)
                    nodes[i][j].neighbours[Constants.EAST] = nodes[i][j + 1];
            }
        }

    }

    public Network(int x_size, int y_size) {
        this(x_size, y_size, 1, 10000);
    }

    /**
     * Route the messages
     *
     * @param messages Assume all the messages have distinct sources
     */
    public boolean route(List<Message> messages) throws SizeLimitExceededException {
        finished.clear();
        resetAllNodes();
        for (Message msg : messages) {
            nodes[msg.src.x][msg.src.y].buffer.add(msg);
        }
        int i;
        for (i = 0; i < timeout && finished.size() < messages.size(); i++) {
            routeStep(i);
        }
        if (i == timeout)
            System.out.println("!!TIMEOUT!!");
        return finished.size() == messages.size();

    }

    public List<NodeInstruction>[][] getInstructions() {
        List<NodeInstruction>[][] instrArray = new List[x_size][y_size];
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                instrArray[i][j] = new ArrayList<>(nodes[i][j].instructions);
            }
        }
        return instrArray;
    }


    private void routeStep(int time) throws SizeLimitExceededException {
        Utils.fill(moved, false);
        boolean at_least_moved;
        do {
            at_least_moved = false;

            for (int i = 0; i < x_size; i++) {
                for (int j = 0; j < y_size; j++) {
                    //Deal with arrived
                    if (!moved[i][j]) {
                        Message msg = nodes[i][j].arrive(time);
                        if (msg != null) {
                            moved[i][j] = true;
                            at_least_moved = true;
                            finished.add(msg);
                        }
                    }
                    //Route the message
                    if (!moved[i][j]) {
                        int direction = nodes[i][j].route(time);
                        if (direction != -1) {
                            moved[i][j] = true;
                            switch (direction) {
                                case Constants.NORTH:
                                    moved[i - 1][j] = true;
                                    break;
                                case Constants.SOUTH:
                                    moved[i + 1][j] = true;
                                    break;
                                case Constants.EAST:
                                    moved[i][j + 1] = true;
                                    break;
                                case Constants.WEST:
                                    moved[i][j - 1] = true;
                                    break;
                            }
                            at_least_moved = true;
                        }
                    }
                }
            }

        } while (at_least_moved);
    }

    private void resetAllNodes() {
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                nodes[i][j].reset();
            }
        }
    }

}
