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

}