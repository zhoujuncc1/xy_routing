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
