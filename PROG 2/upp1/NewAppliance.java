/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class NewAppliance extends Alert {
    private TextField nameField = new TextField();
    private TextField stoneField = new TextField();
    private TextField priceField = new TextField();

    public NewAppliance(){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Namn"), nameField);
        grid.addRow(1, new Label("Pris"), stoneField);
        grid.addRow(2, new Label("Skick"), priceField);
        getDialogPane().setContent(grid);
        setHeaderText(null);
    }

    public String getName(){
        return nameField.getText();
    }

    public double getPrice(){
        return Double.parseDouble(stoneField.getText());
    }

    public double getWear(){ return Double.parseDouble(priceField.getText());}

}
