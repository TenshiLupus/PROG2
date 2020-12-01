/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NewNamedPlace extends Alert {

    //Class similar to new Described place although without the description textfield.
    private TextField nameField = new TextField();

    public NewNamedPlace(){
        super(AlertType.CONFIRMATION);
        System.out.println("Alert Created");
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("name"), nameField);
        getDialogPane().setContent(grid);
        setHeaderText(null);
    }

    public String getName(){
        return  nameField.getText();
    }
}
