import java.util.Collections;
import java.util.Stack;

/**
 * State class defines the puzzle world.
 * Puzzle defined with matrix of blocks.
 * Agent block is also defined as an external variable for ease of access.
 * Boolean movable variables created for actions.
 * If agent can move a direction (agent position is not on border) then moveable variable set to true.
 * @author VedFI
 */

public class State {
    private Block[][] puzzle;
    private Block agent;
    private int grid_size;
    private boolean movableDown,movableUp,movableLeft,movableRight;

    /**
     * Constructor for State Class
     * Create State object with this constructor only if
     * generateRandomState() or generateGoalState() methods will be used.
     */
    public State(int grid_size){
        this.grid_size = grid_size;
        puzzle = new Block[grid_size][grid_size];
    }

    /**
     * Constructor for State Class
     * Create State object with this constructor if
     * matrix of blocks will be given.
     * This constructor finds agent in matrix and assigns it to external agent object automatically.
     */
    public State(Block[][] puzzle){
        grid_size = puzzle.length;
        this.puzzle = puzzle;
        for(int x=0; x<grid_size; x++) {
            for (int y = 0; y < grid_size; y++) {
                if(puzzle[x][y].getValue()==0){
                    agent = puzzle[x][y];
                }
            }
        }
    }

    /**
     * Generates a random state. (Actually converts state object that run this method.)
     * Sets agent position to the middle. Assigns agent to external object as well.
     * Generated state very likely be unsolvable. Check before using search methods.
     */
    public void generateRandomState(){
        Stack<Block> randomBlocks = new Stack<>();
        Block temp = null;
        for(int i=1; i<(grid_size*grid_size); i++){
            randomBlocks.add(new Block(i,-1,-1));
        }
        Collections.shuffle(randomBlocks);
        for(int x=0; x<grid_size; x++){
            for(int y=0; y<grid_size; y++){
                if(x == grid_size/2 && y == grid_size/2){
                    temp = new Block(0,x,y);
                    puzzle[x][y] = temp;
                    agent = temp;
                }
                else{
                    temp = randomBlocks.pop();
                    temp.setX(x);
                    temp.setY(y);
                    puzzle[x][y] = temp;
                }
            }
        }
    }

    /**
     * Generates a goal state. (Actually converts state object that run this method.)
     */
    public void generateGoalState(){
        for(int x=0; x<grid_size; x++) {
            for (int y = 0; y<grid_size; y++) {
                if(x == 0 && y ==0){
                    agent = new Block(0,x,y);
                    puzzle[x][y] = agent;
                }
                else{
                    int temp = x*grid_size + y;
                    puzzle[x][y] = new Block(temp,x,y);
                }
            }
        }
    }

    /**
     * Checks for if state is goal state.
     * @return true if state is goal.
     */
    public boolean isGoal(State goal){
        for(int x=0; x < grid_size; x++) {
            for (int y = 0; y < grid_size; y++) {
                if(puzzle[x][y].getValue() != goal.getPuzzle()[x][y].getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Replaces the block of matrix at given x & y locations.
     * @param value: numeric value of new block.
     */
    public void setPuzzleCell(int x, int y, int value){
        Block b = new Block(value,x,y);
        puzzle[x][y] = b;
    }

    /**
     * Prints puzzle matrix.
     */
    public void printPuzzle(){
        String out = "";
        for(int x=0; x<grid_size; x++){
            for(int y=0; y<grid_size; y++){
                out+= (puzzle[x][y]==agent) ? "[ # ]" : "[ "+puzzle[x][y].getValue()+" ]";
            }
            out +="\n";
        }
        System.out.println(out);
    }

    /**
     * Checks for agent's position if its on borders.
     * Updates movable variables.
     */
    public void checkMovable(){
        movableLeft = (agent.getY()!=0);
        movableRight = (agent.getY()!=(grid_size-1));
        movableUp = (agent.getX()!=0);
        movableDown = (agent.getX()!=(grid_size-1));
    }

    /**
     * @return Block object on top of agent.
     */
    public Block getUp(){
        if(movableUp){
            return puzzle[agent.getX()-1][agent.getY()];
        }
        else{
            return null;
        }
    }

    /**
     * @return Block object on below of agent.
     */
    public Block getDown(){
        if(movableDown){
            return puzzle[agent.getX()+1][agent.getY()];
        }
        else{
            return null;
        }
    }

    /**
     * @return Block object on left of agent.
     */
    public Block getLeft(){
        if(movableLeft){
            return puzzle[agent.getX()][agent.getY()-1];
        }
        else{
            return null;
        }
    }

    /**
     * @return Block object on right of agent.
     */
    public Block getRight(){
        if(movableRight){
            return puzzle[agent.getX()][agent.getY()+1];
        }
        else{
            return null;
        }
    }

    /**
     * Sets agent object manually.
     * This method should not be used if
     * setPuzzleCell() method won't be called.
     */
    public void setAgent(int x, int y) {
        agent = puzzle[x][y];
    }

    public Block[][] getPuzzle() {
        return puzzle;
    }

    public Block getAgent() {
        return agent;
    }

    public int getGrid_size() {
        return grid_size;
    }

    public boolean isMovableDown() {
        return movableDown;
    }

    public boolean isMovableLeft() {
        return movableLeft;
    }

    public boolean isMovableRight() {
        return movableRight;
    }

    public boolean isMovableUp() {
        return movableUp;
    }
}
