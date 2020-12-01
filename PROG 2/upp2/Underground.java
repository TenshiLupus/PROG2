/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Underground extends Polygon {
    public Underground(int x, int y) {
        super(x, y);
        System.out.println("Im underground");
        setFill(Color.YELLOW);
        setStroke(Color.BLACK);
    }
}
