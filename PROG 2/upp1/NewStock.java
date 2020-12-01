/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class NewStock extends Alert {
    private TextField nameField = new TextField();
    private TextField stoneField = new TextField();
    private TextField priceField = new TextField();

    public NewStock(){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Namn"), nameField);
        grid.addRow(1, new Label("Antal"), stoneField);
        grid.addRow(2, new Label("Pris"), priceField);
        getDialogPane().setContent(grid);
        setHeaderText(null);
    }

    public String getName(){
        return nameField.getText();
    }

    public int getQuantity(){
        return Integer.parseInt(stoneField.getText());
    }

    public int getPrice(){ return Integer.parseInt(priceField.getText());}

}
