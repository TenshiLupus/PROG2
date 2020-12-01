/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.paint.Color;

public class NamedPlace extends Place{

    NamedPlace(Category category, int x, int y, String name){
        super(category, x, y, name);// Due to the class only containing a name, a behaviopur shared by both Described and Named place. This class thus only passes said common parameters to the super constructor
        setFill(Color.BLUE);
        setStroke(Color.BLACK);
    }

    //like previously this class's toString method calls the previously passed values to the super constructor and converts the data to the assembled format.
    @Override
    public String toString(){
        return "Named, " + super.getCategory() + ", " + super.getPosition().getPositionx() + ", " + super.getPosition().getPositiony() + ", " + super.getName();
    }

}


