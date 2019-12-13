import java.util.ArrayList;
import java.util.Collections;

/**
 * Node class defines the tree structure.
 * Every node object has fields for current state,
 * parent node, depth, heuristic cost and action string.
 * @author VedFI
 */

public class Node implements Comparable<Node> {
    private State state;
    private Node parent;
    private int depth,h_cost;
    private String action;

    /**
     * Constructor for Node Class.
     * Depth variable set to 0 if node has not a parent.
     * Otherwise set to parent's depth value plus one.
     */
    public Node(State state, Node parent, String action){
        this.state = state;
        this.parent = parent;
        this.action = action;
        depth = (parent!=null) ? parent.getDepth()+1 : 0;
        h_cost = 0;
    }

    /**
     * @return ArrayList of Nodes from root to node.
     */
    public ArrayList<Node> getPath(){
        ArrayList<Node> path = new ArrayList<>();
        Node temp = this;
        while(temp!=null){
            path.add(temp);
            temp = temp.parent;
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Prints each node object's Action string of path.
     */
    public void printPath(){
        ArrayList<Node> path = getPath();
        String out = "\n";
        for(Node n: path){
            out+= n.action+"-> ";
        }
        out+="GOAL\n";
        System.out.println(out);
    }

    /**
     * Prints each node object's block matrix of path
     */
    public void printStepsOfPath(){
        ArrayList<Node> path = getPath();
        for(Node n: path){
            System.out.println(n.action);
            n.getState().printPuzzle();
        }
    }

    /**
     * Calls directional move methods (moveUp, moveDown, moveLeft, moveRight)
     * And adds to arraylist if they didn't return null.
     * @return ArrayList of child nodes.
     */
    public ArrayList<Node> move(){
        ArrayList<Node> child_nodes = new ArrayList<>();
        if(moveLeft()!=null) child_nodes.add(moveLeft());
        if(moveRight()!=null) child_nodes.add(moveRight());
        if(moveUp()!=null) child_nodes.add(moveUp());
        if(moveDown()!=null) child_nodes.add(moveDown());
        return child_nodes;
    }

    /**
     * @return Children node object when parent move to left.
     */
    public Node moveLeft(){
       state.checkMovable();
       if(state.isMovableLeft()){
           Block[][] puzzle = new Block[state.getGrid_size()][state.getGrid_size()];
           for(int x=0;x<state.getGrid_size();x++){
               for(int y=0;y<state.getGrid_size();y++){
                   puzzle[x][y] = state.getPuzzle()[x][y];
               }
           }
           State s = new State(puzzle);
           Node child = null;
           Block b,agent;
           b = state.getLeft();
           agent = state.getAgent();
           s.setPuzzleCell(agent.getX(),agent.getY(),b.getValue());
           s.setPuzzleCell(b.getX(),b.getY(),0);
           s.setAgent(b.getX(),b.getY());
           child = new Node(s,this,"LEFT");
           return child;
       }
       else{
           return null;
       }
    }

    /**
     * @return Children node object when parent move to right.
     */
    public Node moveRight(){
        state.checkMovable();
        if(state.isMovableRight()){
            Block[][] puzzle = new Block[state.getGrid_size()][state.getGrid_size()];
            for(int x=0;x<state.getGrid_size();x++){
                for(int y=0;y<state.getGrid_size();y++){
                    puzzle[x][y] = state.getPuzzle()[x][y];
                }
            }
            State s = new State(puzzle);
            Node child = null;
            Block b,agent;
            b = state.getRight();
            agent = state.getAgent();
            s.setPuzzleCell(agent.getX(),agent.getY(),b.getValue());
            s.setPuzzleCell(b.getX(),b.getY(),0);
            s.setAgent(b.getX(),b.getY());
            child = new Node(s,this,"RIGHT");
            return child;
        }
        else{
            return null;
        }
    }

    /**
     * @return Children node object when parent move to up.
     */
    public Node moveUp(){
        state.checkMovable();
        if(state.isMovableUp()){
            Block[][] puzzle = new Block[state.getGrid_size()][state.getGrid_size()];
            for(int x=0;x<state.getGrid_size();x++){
                for(int y=0;y<state.getGrid_size();y++){
                    puzzle[x][y] = state.getPuzzle()[x][y];
                }
            }
            State s = new State(puzzle);
            Node child = null;
            Block b,agent;
            b = state.getUp();
            agent = state.getAgent();
            s.setPuzzleCell(agent.getX(),agent.getY(),b.getValue());
            s.setPuzzleCell(b.getX(),b.getY(),0);
            s.setAgent(b.getX(),b.getY());
            child = new Node(s,this,"UP");
            return child;
        }
        else{
            return null;
        }
    }

    /**
     * @return Children node object when parent move to down.
     */
    public Node moveDown(){
        state.checkMovable();
        if(state.isMovableDown()){
            Block[][] puzzle = new Block[state.getGrid_size()][state.getGrid_size()];
            for(int x=0;x<state.getGrid_size();x++){
                for(int y=0;y<state.getGrid_size();y++){
                    puzzle[x][y] = state.getPuzzle()[x][y];
                }
            }
            State s = new State(puzzle);
            Node child = null;
            Block b,agent;
            b = state.getDown();
            agent = state.getAgent();
            s.setPuzzleCell(agent.getX(),agent.getY(),b.getValue());
            s.setPuzzleCell(b.getX(),b.getY(),0);
            s.setAgent(b.getX(),b.getY());
            child = new Node(s,this,"DOWN");
            return child;
        }
        else{
            return null;
        }
    }

    /**
     * heuristic function for A* search
     * Calculates every block object's distance between current state and goal state
     * With Manhattan Distance
     * Then updates h_cost variable with calculated value.
     */
    public void h1(){
        int t,tx,ty;
        h_cost = depth;
        for(int x=0;x<state.getGrid_size();x++){
            for(int y=0;y<state.getGrid_size();y++){
                t = state.getPuzzle()[x][y].getValue();
                tx = t/state.getGrid_size();
                ty = t - (tx*state.getGrid_size());
                h_cost += Math.abs(x-tx) + Math.abs(y-ty);
            }
        }
    }

    /**
     * heuristic function for A* search
     * Calculates the number of blocks that their position is not on goal position.
     * Then updates h_cost variable with calculated value.
     */
    public void h2(){
        int t,tx,ty;
        h_cost = depth;
        for(int x=0;x<state.getGrid_size();x++) {
            for (int y = 0; y < state.getGrid_size(); y++) {
                t = state.getPuzzle()[x][y].getValue();
                tx = t/state.getGrid_size();
                ty = t - (tx*state.getGrid_size());
                if(x != tx || y != ty){
                    h_cost++;
                }
            }
        }
    }

    /**
     * heuristic function for A* search
     * Calculates number of neighbor blocks that their position is not on goal position.
     * Then updates h_cost variable with calculated value.
     */
    public void h3(){
        int t,tx,ty;
        h_cost = depth;
        state.checkMovable();
        if(state.isMovableDown()){
            Block b = state.getDown();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            if(b.getX() != tx || b.getY() != ty){
                h_cost++;
            }
        }
        if(state.isMovableUp()){
            Block b = state.getUp();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            if(b.getX() != tx || b.getY() != ty){
                h_cost++;
            }
        }
        if(state.isMovableLeft()){
            Block b = state.getLeft();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            if(b.getX() != tx || b.getY() != ty){
                h_cost++;
            }
        }
        if(state.isMovableRight()){
            Block b = state.getRight();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            if(b.getX() != tx || b.getY() != ty){
                h_cost++;
            }
        }
    }

    /**
     * heuristic function for A* search
     * Calculates the distance between neighbor blocks with their goal positions.
     * Then updates h_cost variable with calculated value.
     */
    public void h4(){
        int t,tx,ty;
        h_cost = depth;
        state.checkMovable();
        if(state.isMovableDown()){
            Block b = state.getDown();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            h_cost += Math.abs(b.getX()-tx) + Math.abs(b.getY()-ty);
        }
        if(state.isMovableUp()){
            Block b = state.getUp();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            h_cost += Math.abs(b.getX()-tx) + Math.abs(b.getY()-ty);
        }
        if(state.isMovableLeft()){
            Block b = state.getLeft();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            h_cost += Math.abs(b.getX()-tx) + Math.abs(b.getY()-ty);
        }
        if(state.isMovableRight()){
            Block b = state.getRight();
            t = b.getValue();
            tx = t/state.getGrid_size();
            ty = t - (tx*state.getGrid_size());
            h_cost += Math.abs(b.getX()-tx) + Math.abs(b.getY()-ty);
        }
    }

    /**
     * compareTo method defined for Priority Queue that used in A* search.
     * Nodes that has lesser h_cost value, has more priority in queue.
     * When h_costs are the same, Then their depth is compared.
     * Lesser depth means more priority in this case.
     */
    @Override
    public int compareTo(Node n) {
        if(this.h_cost == n.getH_cost()){       //handle if h_costs are same
            return Integer.compare(n.getDepth(), this.getDepth());
        }
        else{
            return (this.h_cost-n.getH_cost());
        }
    }

    public int getDepth() {
        return depth;
    }

    public int getH_cost() {
        return h_cost;
    }

    public State getState() {
        return state;
    }


}
