/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

abstract class Place extends Polygon {

    private static final int HEIGHTAXIS = 30;// Height of the the 2 adjecent polygons away from the main point.
    private static final int WIDTHAXIS = 15;// Side direction of the 2 other vertices
    private static int marked;//Debugging variable, it is not intended to be part of the program.
    private Position location;
    private String name;
    private Category category;
    private boolean selected;

    Place(Category category, int x, int y, String name) {
        super(x, y, x - WIDTHAXIS, y - HEIGHTAXIS, x + WIDTHAXIS, y - HEIGHTAXIS);
        setFill(Color.BLUE);
        setStroke(Color.BLACK);
        this.category = category;
        this.name = name;
        this.location = new Position(x, y);// passes over the coordinates to a position object containing methods for retrieval of coordinates. /
        this.setOnMouseClicked(new SelectionHandler());// Alocates a Mouse event on instanciastion of new palces.
        selected = false; //Set the selection state to false;
    }

    //returns the name of the place. Since the other two places type don't differ much in the necessities under execution. The method remains as non abstract, indicating that its subclasses don't do not necessarily need to change the content of the super class method.
    public String getName(){return this.name;}

    //Controlls if the instanciated place was initiated as null first. Else it returns the category given on creation.
    public Category getCategory(){
        if(category == null)return null;
        return this.category;
    }

    //returns the Position object reference containing the coordinates of the place.
    public Position getPosition(){
        return this.location;
    }

    //public boolean isSelected(){return this.selected;} Method never used.

    //Depending on the state of an object this method is used to change the data of the current place.
    public void setSelected(boolean state){
        this.selected = state;
        if (selected) {
            marked++;
            setFill(Color.BLUE);
        } else {
            marked--;
            Program.setPlaceColor(Place.this); // calls the static method within the program class that changes the color of the object depending on the passed in Place type.
        }

    }

    //Depending on the state of this classes global variable Selected, change the state of the object according to the desirable behaviour.
    class SelectionHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.PRIMARY) {//Executes the indented code below if the event detects the left mouse button has been pressed.
                if (!selected) {
                    setSelected(true);
                    Program.getMarkedPlaces().add(Place.this);//Returns a hashset containing all the currently selected places.
                }
                else if (selected) {
                    setSelected(false);
                    Program.getMarkedPlaces().remove(Place.this);
                }
                System.out.println(Program.getMarkedPlaces());// debbuging
                System.out.println("Marked places static " + marked +" Size of marked Places is " + Program.getMarkedPlaces().size());//Debbuging.
            }
            else if(event.getButton() == MouseButton.SECONDARY){//Executes the indented code below if the event detects the right mouse button has been pressed.
                System.out.println("Right button clicked");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(this.toString());//Call the toString method of this place and displays it on the alert window.
                alert.showAndWait();
                System.out.println("Information should have happened");
            }
        }

        //Depending on the instance of the object the format will change accordingly to the data.
        @Override
        public String toString(){
            //if current Place is an instance of Named place, the attributes corresponding to the subclass are called and returned to be visible when the toString method is called
            if(Place.this instanceof NamedPlace){
                return "Name: " + Place.this.getName() + "[" + Place.this.getPosition().getPositionx() + ", " + Place.this.getPosition().getPositiony() + "]";
            }
            //if the place is an instance of describedPlace the corresponding attributes are displayed in the same manner as the block above.
            else if(Place.this instanceof DescribedPlace){
                return "Name: " + Place.this.getName() + " [" + Place.this.getPosition().getPositionx() + ", " + Place.this.getPosition().getPositiony() + "]" + "\n" + "Description: " + ((DescribedPlace)Place.this).getDescription();
            }
            //return null.
            return null;
        }
    }
}

