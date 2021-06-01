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

public class Constants {

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int WEST = 2;
    public static final int SOUTH = 3;
    
    public static final String[] DIRECTION_LETTER = {"N", "E", "W", "S"};
    public static final String[] DIRECTION_WORD = {"NORTH", "EAST", "WEST", "SOUTH"};

    public static final int INSTR_SEND = 0;
    public static final int INSTR_BYPASS = 1;
    public static final int INSTR_RECV = 2;
    public static final int INSTR_FWD = 3;

}