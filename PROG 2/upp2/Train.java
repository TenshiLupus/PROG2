/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Train extends Polygon {
    public Train(int x, int y) {
        super(x,y);
        setFill(Color.GREEN);
        setStroke(Color.BLACK);
        System.out.println("Im train");
    }
}
