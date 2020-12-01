/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CoordinateWindow extends Alert {
    private TextField xaxisField = new TextField();
    private TextField yaxisField = new TextField();

    public CoordinateWindow() {
        super(AlertType.CONFIRMATION);
        System.out.println("Alert created");
        GridPane grid = new GridPane();
        System.out.println("Window crated");
        grid.addRow(0, new Label("X:"), xaxisField);
        grid.addRow(1, new Label("Y:"), yaxisField);
        System.out.println("Labels created?");
        getDialogPane().setContent(grid); //Sets the content of the pop up window to the assigned parameter
        System.out.println("Grid added to content");
        setHeaderText(null);
        System.out.println("Coordinates window created");

    }
    //returns the written value on the textfield X
    public int getLocationX(){
        return Integer.parseInt(xaxisField.getText());
    }
    //returns the written value on the textfield Y
    public int getLocationy(){
        return Integer.parseInt(yaxisField.getText());
    }
}
