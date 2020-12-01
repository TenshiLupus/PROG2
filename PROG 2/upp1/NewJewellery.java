/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class NewJewellery extends Alert {
    private TextField nameField = new TextField();
    private TextField stoneField = new TextField();
    private CheckBox ofGoldBox = new CheckBox("Av guld");

    public NewJewellery(){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Namn"), nameField);
        grid.addRow(1, new Label("Stenar"), stoneField);
        grid.addRow(2, ofGoldBox);
        getDialogPane().setContent(grid);
        setHeaderText(null);
    }

    public String getName(){
        return nameField.getText();
    }

    public int getStones(){
        return Integer.parseInt(stoneField.getText());
    }

    public String getGold(){

        if(ofGoldBox.isSelected())return "Guld";
        else return "Silver";
    }
}
