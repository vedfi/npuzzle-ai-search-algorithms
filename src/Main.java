import java.lang.management.ManagementFactory;
/**
 * Create start and goal states.
 * Create a root node with start state.
 * Create a search object.
 * run a search function.
 * @author VedFI
 */
public class Main {
    public static void main(String[] args){
        /**
         * Define start state as:
         *      [3] [2] [5]
         *      [7] [#] [8]     note that #'s value should be given 0.
         *      [4] [1] [6]
         *
         * Start state can be randomly created but state very likely be unsolvable.
         */
        Block[][] puzzle = {
                {new Block(3,0,0),new Block(2,0,1), new Block(5,0,2)},
                {new Block(7,1,0),new Block(0,1,1), new Block(8,1,2)},
                {new Block(4,2,0),new Block(1,2,1), new Block(6,2,2)}};
        State start = new State(puzzle);
        State goal = new State(3);
        goal.generateGoalState();
        System.out.println("-START STATE-");
        start.printPuzzle();
        System.out.println("-GOAL STATE-");
        goal.printPuzzle();
        Node root = new Node(start,null,"START");
        Search search = new Search(root,goal);
        long t1 = System.currentTimeMillis();
        search.AStar();
        long t2 = System.currentTimeMillis();
        System.out.println("Used Memory: "+(float)ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/1000000+" MB");
        System.out.println("Time: "+(t2-t1));
    }

}


