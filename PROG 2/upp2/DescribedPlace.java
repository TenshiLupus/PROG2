/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.paint.Color;

public class DescribedPlace extends Place {
    private String description;

    DescribedPlace(Category category, int x, int y, String name, String description){
        super(category, x, y, name); //passes the common variables to the super constructor
        this.description = description;// description belongs to this concrete class thus why it is declared in this class.
        setFill(Color.BLUE);//Place is initiliazed as unmarked, categorized by it being blue.
        setStroke(Color.BLACK);

    }

    // calls the previously passed values to the super constructor, with the super keyword and displays them on the toString method.
    public String getDescription(){
        return this.description;
    }
    @Override
    public String toString(){
        return "Described, " + super.getCategory() + ", " + super.getPosition().getPositionx() + ", " + super.getPosition().getPositiony() + ", " + this.description;
    }
}