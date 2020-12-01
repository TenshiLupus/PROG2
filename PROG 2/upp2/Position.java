/**
 * @author Angel Cardenas Martinez anca8079
 */
import java.util.Objects;

public class Position {
    private int positionx;
    private int positiony;

    public Position(int x, int y){
        this.positionx = x;
        this.positiony = y;
    }

    public int getPositionx(){
        return positionx;
    }//returns the int value of x

    public int getPositiony(){
        return positiony;
    }//returns the int value of y

    @Override
    public String toString(){
        return this.positionx + "," + this.positiony;
    }//returns the String format of position and Y

    //Overriden equals method for comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return positionx == position.positionx &&
                positiony == position.positiony;
    }

    //Returns the hashcode value of both position x and y and is used for hashing.
    @Override
    public int hashCode() {
        return Objects.hash(positionx, positiony);
    }
}
