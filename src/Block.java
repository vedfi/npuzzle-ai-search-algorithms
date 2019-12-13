/**
 * Block class defines unit of puzzle matrix.
 * Variables x and y hold the position of the block in the matrix.
 * Value holds the number of block. (numeric value)
 * @author VedFI
 */
public class Block {
    private int x,y,value;

    public Block(int value,int x,int y){
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
