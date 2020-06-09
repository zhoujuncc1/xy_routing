# Shenjing NoC routing simulator
This is the NoC routing simulator developed for Shenjing neuromorphic accelerator. It can also be used for general network simulations. It currently supports:
1. Mesh network;
2. Simple XY routing;
3. User defined buffer size.

The simulator takes messages to transit as input and output cycle-by-cycle instructions of every router.

The interface of simulator is at sg.edu.nus.comp.shenjing_routing.noc.Simulator 

## Input Format
* Each line contains a message in the format of src_x,src_y,dst_x,dst_y.
* For example "1,2,2,3" represents the message from (1,2) to (2,3).
* Messages are separated into groups by blank lines. Those from one group are to be routed in parallel.
 
 Sample:
 ~~~
 0,1,0,0
 0,3,0,2
 1,1,1,0
 1,3,1,2
 
 0,2,0,0
 1,2,1,0
 ~~~
 
## Output Instructions
* The simulator output instructions of all routers in lines. 
* Instructions of different routers are separated by a blank line.
* Each instruction is encoded as:
~~~
$TIME(int),$TYPE(int{0-SEND, 1-BYPASS, 2-RECV}),$SRC_DIR(char{0,N,S,W,E}),$DST_DIR,MSG_ID(int)
~~~