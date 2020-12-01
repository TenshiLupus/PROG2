/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NewDescribedPlace extends Alert {

    private TextField nameField = new TextField();//Displays a textfield where the name of the place should be written on popup
    private TextField descriptionField = new TextField();//Displays a textfield Where the the description of the place should be written on popup

    public NewDescribedPlace(){
        super(AlertType.CONFIRMATION);
        System.out.println("Alert created");
        GridPane grid = new GridPane(); //Grid Pane allows the placement of nodes in an ordered manner.
        grid.addRow(0, new Label("name"), nameField); //Line of content of row 0
        grid.addRow(1, new Label("Description"), descriptionField); // Line of content of row 1
        getDialogPane().setContent(grid);
        setHeaderText(null);//Removes the standard blue circle in header
    }

    //returns the content of the name field as a String.
    public String getName(){
        return  nameField.getText();
    }
    //returns the content of the description field as a string.
    public String getDescription(){
        return  descriptionField.getText();
    }
}
