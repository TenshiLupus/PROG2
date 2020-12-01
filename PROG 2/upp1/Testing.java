/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.Optional;
import java.util.ArrayList;

public class Testing extends Application {
    private static final int WIDTH = 400;
    private static final int HEIGTH = 600;
    private ArrayList<Valuable> valuables = new ArrayList<>();
    private TextArea display;

    private TextField textField;
    private MenuButton menuButton = new MenuButton("Välj");
    private RadioButton name;
    private RadioButton value;


    public void start(Stage primaryStage) {
        //create a Scene
        BorderPane window = new BorderPane();
        primaryStage.setTitle("Sakregister");
        setTopPane(window);
        //Right side
       setRightPane(window);
        //center
        setCenterDisplay(window);
        setBottomMenu();
        setBottomButtons(window);
        Scene scene = new Scene(window, WIDTH, HEIGTH);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setCenterDisplay(BorderPane window){
        display = new TextArea();
        window.setCenter(display);
        display.setEditable(false);
    }

    private void setBottomButtons(BorderPane window){
        Label labelNy = new Label("Ny");
        Button buttonB = new Button("Visa");
        buttonB.setOnAction(new ListHandler());
        Button buttonC = new Button("Börskrasch");
        buttonC.setOnAction(new KraschHandler());
        FlowPane bottom = new FlowPane();
        window.setBottom(bottom);
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(labelNy, menuButton, buttonB, buttonC);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setHgap(5);
    }

    private void setBottomMenu(){
        MenuItem item1 = new MenuItem("Smycke");
        item1.setOnAction(new Item1Handler());
        MenuItem item2 = new MenuItem("Aktie");
        item2.setOnAction(new Item2Handler());
        MenuItem item3 = new MenuItem("Apparat");
        item3.setOnAction(new Item3Handler());
        menuButton.getItems().addAll(item1, item2, item3);
    }

    private void setTopPane(BorderPane window){
        FlowPane top = new FlowPane();
        Label heading = new Label("Värdesaker");
        window.setTop(top);
        top.getChildren().add(heading);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(10, 10, 10, 10));
    }

    private void setRightPane(BorderPane window){
        VBox rightMenu = new VBox();
        window.setRight(rightMenu);
        Label listSort = new Label("Sortering");
        name = new RadioButton("Namn");
        value = new RadioButton("Värde");
        rightMenu.getChildren().addAll(listSort, name, value);
        rightMenu.setPadding(new Insets(10, 10, 10, 10));
        rightMenu.setSpacing(10);
        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(name, value);
        name.setSelected(true);
    }

    public static void main(String[] args) {

        launch(args);
    }

    class ValueSorter implements  Comparator<Valuable>{

        @Override
        public int compare(Valuable v1, Valuable v2) {
            double value = v1.getValue() - v2.getValue();
            System.out.println(value);
            if(value > 0)return 1;
            if(value < 0)return -1;
            return 0;
        }
    }

    class NameSorter implements Comparator<Valuable>{

        @Override
        public int compare(Valuable v1, Valuable v2) {
            return v1.getName().compareTo(v2.getName());
        }
    }

    class Item1Handler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                NewJewellery d1 = new NewJewellery();
                d1.setTitle("Ny Smycke");
                Optional<ButtonType> answer = d1.showAndWait();
                if (answer.isPresent() && answer.get() == ButtonType.OK) { //checkc if the button confirmation window is open and the requested button has been pressed.
                    String name = d1.getName();
                    if (name.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Namn får ej vara tomt!");
                        alert.showAndWait();
                        return;
                    }
                    int stenar = d1.getStones();
                    String gold = d1.getGold();
                    Jewellery jewel = new Jewellery(name, stenar, gold);
                    valuables.add(jewel);
                }
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ej numerisk inmatning");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    class Item2Handler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                NewStock d2 = new NewStock();
                d2.setTitle("Ny aktie");
                Optional<ButtonType> answer = d2.showAndWait();
                if (answer.isPresent() && answer.get() == ButtonType.OK){ //checks if the button confirmation window is open and the requested button has been pressed.
                    String name = d2.getName();
                    if(name.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Felaktig inmatning!");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        return;
                    }
                    int quantity = d2.getQuantity();
                    double price = d2.getPrice();
                    Stock stock = new Stock(name, quantity, price);
                    valuables.add(stock);
                }
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Felaktig inmatming!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    class Item3Handler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                NewAppliance d3 = new NewAppliance();
                d3.setTitle("Ny apparat");
                Optional<ButtonType> answer = d3.showAndWait();
                if (answer.isPresent() && answer.get() == ButtonType.OK){ //checks if the button confirmation window is open and the requested button has been pressed.
                    String name = d3.getName();
                    if(name.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Felaktig inmatning!");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        return;
                    }
                    double price = d3.getPrice();
                    double wear = d3.getWear();
                    if(wear > 10 || wear < 0){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Wear får bara vara inom 10 eller 0");
                        alert.showAndWait();
                        return;
                    }
                    Appliance appliance = new Appliance(name, price, wear);
                    valuables.add(appliance);
                }
            }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Felaktig inmatming!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    class ListHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            display.clear();
            if(name.isSelected())valuables.sort(new NameSorter());
            else if (value.isSelected())valuables.sort(new ValueSorter());
            for(Valuable item : valuables ){
                display.appendText(item.toString() + "\n");
            }
        }
    }

    class KraschHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            for(Valuable s : valuables){
                if(s instanceof Stock){
                    ((Stock) s).setRate(0);
                }
            }
        }
    }
}
