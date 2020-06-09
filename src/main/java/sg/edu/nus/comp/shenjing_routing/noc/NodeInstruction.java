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

/**
 *
 */
public class NodeInstruction {


    public int time;
    public int type;
    public int src_direction;
    public int dst_direction;
    public int message_id;


    public NodeInstruction(int time, int type, int src_direction, int dst_direction, int message_id) {
        this.time = time;
        this.type = type;
        this.src_direction = src_direction;
        this.dst_direction = dst_direction;
        this.message_id = message_id;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(time).append(", ");
        switch (type) {
            case Constants.INSTR_SEND:
                stringBuilder.append("SEND, ");
                break;
            case Constants.INSTR_RECV:
                stringBuilder.append("RECV, ");
                break;
            case Constants.INSTR_BYPASS:
                stringBuilder.append("BYPASS, ");
                break;
        }
        if (src_direction != -1)
            stringBuilder.append(src_direction).append(", ");
        if (dst_direction != -1)
            stringBuilder.append(dst_direction).append(", ");
        stringBuilder.append(message_id);
        return stringBuilder.toString();
    }


    /**
     * @return encoded instruction as string: $TIME(int),$TYPE(int{0-SEND, 1-BYPASS, 2-RECV}),$SRC_DIR(char{0,N,S,W,E}),$DST_DIR,MSG_ID(int)
     */
    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(time).append(",");
        stringBuilder.append(type).append(",");
        stringBuilder.append(Network.directionToString(src_direction, true)).append(",");
        stringBuilder.append(Network.directionToString(dst_direction, true)).append(",");
        stringBuilder.append(message_id);
        return stringBuilder.toString();
    }
}
