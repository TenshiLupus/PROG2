/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Bus extends Polygon {
    public Bus(int x, int y) {
        super(x, y);//passes over the position of this place to the super class Polygon
        setFill(Color.RED); // calls inherited method from polygon and fills the shape with the given color
        setStroke(Color.BLACK); // creates a stroke around the border of the place
        System.out.println("Im bus"); //debugging
    }
}
