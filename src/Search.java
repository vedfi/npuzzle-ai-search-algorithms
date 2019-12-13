import java.util.*;
/**
 * Search class provides methods for;
 * A* Search,Iterative Deepening Search, Depth First Search and Breadth First Search algorithms.
 * Search objects has fields for Root Node, Goal State and Number of Expanded nodes.
 * @author VedFI
 */
public class Search {
    private Node root;
    private State goal;
    private int nodes_expanded;

    public Search(Node root, State goal){
        this.root = root;
        this.goal = goal;
        nodes_expanded = 0;
    }

    /**
     * Nodes stored in a priority queue (look for compareTo() method in Node.java)
     * Child nodes add to queue right after their heuristic cost values calculated.
     * @return Node if a solution found. Else returns null.
     */
    public Node AStar(){
        PriorityQueue<Node> nodes = new PriorityQueue<>();
        ArrayList<Node> child;
        nodes.add(root);
        try{
            while(!nodes.isEmpty()){
                Node n = nodes.poll();
                if(n.getState().isGoal(goal)){
                    System.out.println("A* Found A Solution!" +
                            "\nCost & Depth: " + n.getDepth() +
                            "\nTotal Nodes Expanded: " + nodes_expanded);
                    Thread.sleep(1000);
                    //n.printPath();
                    //n.printStepsOfPath();
                    return n;
                }
                else{
                    child = n.move();
                    for(Node c:child){
                        c.h1();         //heuristic function called here, could be changed.
                        nodes.add(c);
                    }
                    nodes_expanded++;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Nodes stored in a FIFO List. In this case Queue used.
     * @return Node if a solution found. Else returns null.
     */
    public Node BFS(){
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);
        try {
            while (!nodes.isEmpty()) {
                Node n = nodes.poll();
                if (n.getState().isGoal(goal)) {
                    System.out.println("BFS Found A Solution!" +
                            "\nCost & Depth: " + n.getDepth() +
                            "\nTotal Nodes Expanded: " + nodes_expanded);
                    Thread.sleep(1000);
                    //n.printPath();
                    //n.printStepsOfPath();
                    return n;
                } else {
                    nodes.addAll(n.move());
                    nodes_expanded++;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Nodes stored in a LIFO list. In this case Stack used.
     * Child nodes selected randomly. Because in our puzzle game
     * there is unlimited number of actions. If we don't randomise
     * child node selection, then DFS won't be complete.
     * @return Node if a solution found. Else returns null.
     */
    public Node DFS(){
        Stack<Node> nodes = new Stack<>();
        ArrayList<Node> child;
        nodes.add(root);
        try{
            while(!nodes.isEmpty()){
                Node n = nodes.pop();
                if(n.getState().isGoal(goal)){
                    System.out.println("DFS Found A Solution!" +
                            "\nCost & Depth: " + n.getDepth() +
                            "\nTotal Nodes Expanded: " + nodes_expanded);
                    Thread.sleep(1000);
                    //n.printPath();
                    //n.printStepsOfPath();
                    return n;
                }
                else{
                    child = n.move();
                    Collections.shuffle(child);         //randomization
                    nodes.addAll(child);
                    nodes_expanded++;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Nodes stored in a LIFO list. In this case Stack used.
     * Child nodes add to stack if their depth is not greater than depth_limit.
     * If the stack is empty, then depth_limit is increased.
     * @return Node if a solution found. Else returns null.
     */
    public Node IDS(){
        Stack<Node> nodes = new Stack<>();
        ArrayList<Node> child;
        int depth_limit = 0;
        nodes.add(root);
        try{
            while(!nodes.isEmpty()){
                Node n = nodes.pop();
                if(n.getState().isGoal(goal)){
                    System.out.println("IDS Found A Solution With Depth Limit "
                            +depth_limit+" !"+
                            "\nCost & Depth: " + n.getDepth() +
                            "\nTotal Nodes Expanded: " + nodes_expanded);
                    Thread.sleep(1000);
                    //n.printPath();
                    //n.printStepsOfPath();
                    return n;
                }
                else{
                    child = n.move();
                    for(Node c:child){
                        if(c.getDepth() <= depth_limit){
                            nodes.add(c);
                        }
                    }
                    nodes_expanded++;
                }
                if(nodes.isEmpty()){
                    nodes.add(root);
                    depth_limit++;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
