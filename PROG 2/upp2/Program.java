/**
 * @author Angel Cardenas Martinez anca8079
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;
import java.util.*;

public class Program extends Application{

    private static Set<Place> markedPlaces = new HashSet<>();
    //to avoid problems, some values are made into constants and set for reusability.
    private static final int HEIGHT = 900;
    private static final int WIDTH = 1200;
    private static final int FIRSTTOKEN = 0;
    private static final int SECONDTOKEN = 1;
    private static final int THIRDTOKEN = 2;
    private static final int FOURTHTOKEN = 3;
    private static final int FIFTHTOKEN = 4;
    private static final int SIXTHTOKEN = 5;
    //Attributes are that are set to te accessed by outer handlers.
    private boolean changed;
    private RadioButton named;
    private RadioButton described;
    private BorderPane root;
    private Menu fileMenu;
    private MenuBar menuBar;
    private Button newButton;
    private VBox topElements;
    private VBox radioButtons;
    private VBox right;
    private FlowPane top;
    private ToggleGroup group;
    private TextField field;
    private Button search;
    private Button hide;
    private Button remove;
    private Button coordinates;
    private Button hideCategory;
    private Label category;
    private ListView<String> listView;
    private FileChooser fileChooser = new FileChooser();//Initializzes fileChooser as a new FileChooser().
    private Stage primaryStage;
    private ImageView iv;
    private Pane centerPane;
    //Datastructures that are accessed globally
    private HashMap<String, HashSet<Place>> byName = new HashMap<>();
    private HashMap<Category, HashSet<Place>> byCategory = new HashMap<>();
    private HashMap<Position, Place> byPosition = new HashMap<>();



    @Override
    public void start(Stage primaryStage){

        this.primaryStage = primaryStage;
        root = new BorderPane();//create the frame for the nodes to be put on.
        Scene screen = new Scene(root, WIDTH, HEIGHT);//Sets the dimensions and the frame in the Scene to be displayed.
        primaryStage.setScene(screen); //puts the contents of the scene into a window.
        assembleTopBorder();//assembles the top components of the window.
        assembleRightDisplay();//Assembles the right side components of the window.
        assembleCenter();//Assembles all the components in the middle part of the window.
        primaryStage.setTitle("File Screen");//Sets the name of the Screen to file Screen
        primaryStage.setOnCloseRequest(new ExitHandler());//Calls the Exithandler().
        primaryStage.show();//Calls the show method.
        changed = false;//initializes changed as false.
    }

    private void assembleTopBorder() {
        topElements = new VBox();//Crates a vetical box to put nodes into.
        fileMenu = new Menu("File");//Creates a menu dropdownbox on
        MenuItem option1 = new MenuItem("Load Map");//creates a menuitem 1.
        option1.setOnAction(new LoadHandler());//sets functionality for the option1 menu items to call the LoadHandler().
        MenuItem option2 = new MenuItem("Load Places");//Creates a option 2 MenuItem.
        option2.setOnAction(new OpenHandler());//sets functionality to call the option 2 menu item to call the OpenHandler().
        MenuItem option3 = new MenuItem("Save");//Creates a option 3 MenuItem.
        option3.setOnAction(new SaveHandler());//sets functionality to call the option 3 menu item to call the SaveHandler().
        MenuItem option4 = new MenuItem("Exit");//Creates a option 4 MenuItem.
        option4.setOnAction(new ExitItemHandler());//sets functionality to call the option 4 menuItem to call the ExitItemHandler().
        fileMenu.getItems().addAll(option1, option2, option3, option4);//add menuitems to the dropdown menu
        menuBar = new MenuBar();//Creates a menubar to put the menu object into.
        menuBar.getMenus().add(fileMenu);//Adds the dropdownbox with it items to the menubar object.
        root.setTop(menuBar);//Sets the menu Bar to the top of the window.
        assembleButtons();//Assembles the components that are going to tbe visible at the top region of the window.
        topElements.getChildren().addAll(menuBar, top);//Calls The topElemts Vbox and adds the menubar and the other components into the top elements node.
        root.setTop(topElements);//Sets the top elements on the top region of the frame.
    }

    private void assembleButtons(){
        top = new FlowPane();// creates a new Flowpane to add items into.
        root.setTop(top);//Sets the flowpane on the top Region of the frame.
        newButton = new Button("New");//creates a button to be pressed.
        newButton.setOnAction(new NewButtonHandler());//Sets a Handler to newButton.
        top.setAlignment(Pos.TOP_CENTER);//Sets the alignment of the FlowPane to the top.
        field = new TextField("Search");//Creates a new Textfield to be put into the.
        search = new Button("Search");//New a searchButton.
        search.setOnAction(new SearchHandler());//sets functionality of SearchHandler().
        hide = new Button("Hide");//Creates Button with the hide text.
        hide.setOnAction(new HideHandler());//Sets functionality of HideHandler();
        remove = new Button("Remove");//creates Button with the remove text.
        remove.setOnAction(new RemoveHandler());//sets functionality of removehandler().
        coordinates = new Button("Coordinates");//creates Button the text coordinates.
        coordinates.setOnAction(new CoordinatesHandler());//set the functionality of the coordinate handler on the button.
        assembleRadioButtons();//runs this void method to assembleradiobuttons.
    }

    private void assembleRightDisplay(){
        right = new VBox();//Create a new vertical Box.
        root.setRight(right);//Set the vetical box to the right region of the root frame.
        right.setAlignment(Pos.CENTER);//Set the alignment of the Vertical Box in the right region to Center position.
        category = new Label("Category");//Create a new Label with the text category.
        listView = new ListView<>();//initialize the listView variable as a new ListView.
        listView.getItems().addAll("Bus", "Underground", "Train");//Get items of the listView and add all the strings in its parameters.

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);//Get the selection property of the item and set it so that it can only selct on item at a time.
        listView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {//add a Listener the list selection and depending on the category, gets the corresponding Map and its places and and runs the lambda block.
            if(byCategory.get(getSelectedCategory()) != null){//runs the block while the returned category is not null.
                for (Place place : byCategory.get(getSelectedCategory())) {
                    if (place.getCategory() != null) {
                        place.setVisible(true);
                    }
                }
            }
        });
        hideCategory = new Button("Hide Category");//initializes hideCategory variable as new Button.
        hideCategory.setOnAction(new HideCategoryHandler());//sets functionality of HideCategoryhandler to the HideCategory button.
        right.getChildren().addAll(category, listView, hideCategory);//Adds the components to the Vertical Box in the right region of the frame.
    }

    private void assembleCenter(){
        centerPane = new Pane();//Initialize centerPane as a Pane.
        root.setCenter(centerPane);//Sets the centerPane to the center of the frame.
        //centerTop.setAlignment(Pos.TOP_RIGHT);
    }

    private void assembleRadioButtons(){
        named = new RadioButton("Named");//Initializes named variable as a new RadioButton with the text Named.
        named.setSelected(true);//Set named as selected at the start of the program.
        described = new RadioButton("Described");//Initializes described as a RadioButton with the text described.
        group = new ToggleGroup();//initializes group as a new ToggleGroup.
        group.getToggles().addAll(named,described);//Adds the 2 previously initialized RadioButtons to the toggle group.
        radioButtons = new VBox();// creates a single Vbox that will contain RadioButtons.
        radioButtons.getChildren().addAll(named,described);//Add both radioButtons to the radioButtons VerticalBox
        radioButtons.setPadding(new Insets(10,10,10,10));//Sets the distance of the nodes from eachother to 10 pixels on every direction.
        radioButtons.setSpacing(10);//Sets the spacing between the edges to 10 pixels
        top.getChildren().addAll(newButton, radioButtons, field, search, hide, remove, coordinates);//Adds all the items to the Top Horizontal box.
        top.setPadding(new Insets(10, 10, 10, 10));// tsets the padding of to 10 pixels on every direction
        top.setHgap(10);// set the gap between the nodes to 10 pixels
    }

    //As the name implies returns a hashset of markedPlaces.
    public static Set<Place> getMarkedPlaces(){
        return markedPlaces;
    }

    //As the name implies returns a Category depending on the selected items in the listView, else return null if no categories are selected.
    private Category getSelectedCategory(){
        String selectedItem = listView.getSelectionModel().getSelectedItem();//Get the selected item in the listView.
        if(selectedItem == null)return null;
        else if(selectedItem.equals(Category.Bus.name()))return Category.Bus;
        else if(selectedItem.equals(Category.Underground.name()))return Category.Underground;
        else if(selectedItem.equals(Category.Train.name()))return Category.Train;
        else{
            return null;
        }
    }

    //Depending on the given cateegory of the place at instantiation, proceed with the corresponding block below.
    public static void setPlaceColor(Place place){
        if(place.getCategory() == null){
            place.setFill(Color.BLACK);
            place.setStroke(Color.BLACK);
        }
        else if(place.getCategory().equals(Category.Bus)){
            place.setFill(Color.RED);
            place.setStroke(Color.BLACK);
        }
        else if(place.getCategory().equals(Category.Underground)){
            place.setFill(Color.YELLOW);
            place.setStroke(Color.BLACK);
        }
        else if(place.getCategory().equals(Category.Train)){
            place.setFill(Color.GREEN);
            place.setStroke(Color.BLACK);
        }
    }

    //As the name implies the method creates a new NamedPlace and proceeds tom assign the attributes of the object and places it in a map.
    private void createNamedPlace(Category category, int locationX, int locationY){
        NewNamedPlace newNamedPlace = new NewNamedPlace();//Instantiates a newNamedPlace which creates a alert window.
        Optional<ButtonType> answer = newNamedPlace.showAndWait();//Displays the the window on screen and gives the option to press Ok or cancel.
        if(answer.isPresent() && answer.get() == ButtonType.CANCEL){//if the window is up and the selected answer is cancel.
            root.setCursor(Cursor.DEFAULT);//Set the cursor to default.
            return;//exits the method before preceeding forward.
        }
        else if(answer.isPresent() && answer.get() == ButtonType.OK){//else if the Ok was presseds. Proceeds to the creaion of the object and sets the desired attributes accordingly.
            String name = newNamedPlace.getName();//Sets the name of the place to the variable name.
            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Namn får ej vara tomt");
                alert.showAndWait();
                root.setCursor(Cursor.DEFAULT);//Set the cursor to default.
                return;
            }
            NamedPlace namedPlace = new NamedPlace(category, locationX , locationY, name);//Sets the passed in categories into the new Named Place.
            setPlaceColor(namedPlace);//Sends the object into the SetPlaceColor to set the places colors accordingly.
            centerPane.getChildren().add(namedPlace);//adds the object into the centerPane. And proceeds to add it to the canvas.
            byPosition.put(namedPlace.getPosition(), namedPlace);//Puts the namedPlace into a map with its position as a value.
            HashSet<Place> placesSet = byName.get(namedPlace.getName());//Given that ultiple places may have the same name. Places are set into a hashet that contains unique places, though each with the same name.
            if(placesSet == null){//if the set of places is equal to null, then a new set is created ot replace the null value of the key thus ensuring there is a hashset on the next call.
                placesSet = new HashSet<>();//We initialize placesSet as a new Hashset.
                byName.put(namedPlace.getName(), placesSet);//puts the hashset into the field of the Key value.
            }
            placesSet.add(namedPlace);//adds the new created Object
            HashSet<Place> categorySet = byCategory.get(namedPlace.getCategory());//depending on the category of the object we put the the object on their corresponding hashset returned by the category value.
            if(categorySet == null){//cehcks that the hashSet is not null and as the previous control, behaves in the same way.
                categorySet = new HashSet<>();
                byCategory.put(namedPlace.getCategory(), categorySet);
            }
            categorySet.add(namedPlace);
            root.setCursor(Cursor.DEFAULT);//Sets the cursor to default after completion
            changed = true;//Sets the state of the program to changed after object creation.
        }

    }

    //Creating a described place is similar tot the create Named Place block with the addition of addition of the description attribute.
    private void createDescribedPlace(Category category, int locationX, int locationY){
        NewDescribedPlace newDescribedPlace = new NewDescribedPlace();
        Optional<ButtonType> answer = newDescribedPlace.showAndWait();
        if(answer.isPresent() && answer.get() == ButtonType.CANCEL){
            root.setCursor(Cursor.DEFAULT);
            return;
        }
        else if(answer.isPresent() && answer.get() == ButtonType.OK){
            String name = newDescribedPlace.getName();
            String description = newDescribedPlace.getDescription();
            if(name.isEmpty() || description.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Namn får ej vara tomt");
                alert.showAndWait();
                root.setCursor(Cursor.DEFAULT);//Set the cursor to default.
                return;
            }
            DescribedPlace describedPlace = new DescribedPlace(category, locationX, locationY, name, description);
            setPlaceColor(describedPlace);
            centerPane.getChildren().add(describedPlace);
            byPosition.put(describedPlace.getPosition(), describedPlace);
            HashSet<Place> categorySet = byName.get(describedPlace.getName());
            if(categorySet == null){
                categorySet = new HashSet<>();
                byCategory.put(describedPlace.getCategory(), categorySet);
            }
            categorySet.add(describedPlace);
            HashSet<Place> placesSet = byName.get(describedPlace.getName());
            if(placesSet == null){
                placesSet = new HashSet<>();
                byName.put(describedPlace.getName(), placesSet);
            }
            placesSet.add(describedPlace);
            root.setCursor(Cursor.DEFAULT);
            System.out.println("created described place with category");
            changed = true;
        }
    }

    //As the name implies we are creating a place based on the read information that has been loaded in
    private void createLoadedPlace(String[] tokens){// takes in an a reference to the array of String tokens that has been read.
        Place place = assembleLoadedPlace(tokens);//Returns the assembled place wit the passed in tokens
        centerPane.getChildren().add(place);//adds the place reference to the centerPane.
        if(byName.get(place.getName()) == null)byName.put(place.getName(), new HashSet<>());//if the returned key value is null, we ensure we add a new instance of a hashset for future retrievals.
        byName.get(place.getName()).add(place);//adds the place to the datastructure and sets its name as retrieveal value in a map.
        if(byCategory.get(place.getCategory()) == null) byCategory.put(place.getCategory(), new HashSet<>());//repeats the same process as the code above.
        byCategory.get(place.getCategory()).add(place);//Adds the palce to the data structure for later retrieval.
        byPosition.put(place.getPosition(), place);//Puts the place it a map datastructure with the places position as its value.
        place.relocate(place.getPosition().getPositionx(), place.getPosition().getPositiony());//once the place has been added to the centerpane, relocate its position to coincide with the center panes dimensions.
    }

    private Place assembleLoadedPlace(String[] tokens){//Together with the loaded place, we further pass in the Tokens in the array which come in order, thus why we use constants in the array index..
        String placeType = tokens[FIRSTTOKEN];//The first line specifying the placetype is the first token in the read file.
        Category category = Category.parseCategory(tokens[SECONDTOKEN]);//Since the Category enum didn't have a parser. A parseCategory metyhod was created within the enum for it to return a ceteogy.
        int locationX = Integer.parseInt(tokens[THIRDTOKEN]);//Sets the third token in the array as an integer.
        int locationY = Integer.parseInt(tokens[FOURTHTOKEN]);//Sets the fourth token in the array as an integer.
        String name = tokens[FIFTHTOKEN];//Sets the fifth token in the array into the name variable.
        String description;// creates a local description variable but is never initialized.
        if(placeType.equals("Described")){//Depending if the first token equals the described, the compiler proceeds with the block below.
            description = tokens[SIXTHTOKEN];//initialize description as the sith token in the passed in arraylist.
            DescribedPlace describedPlace = new DescribedPlace(category, locationX, locationY, name, description);//Creates a new described place according to the passed in parameters
            setPlaceColor(describedPlace);//Sets the color of the place accordingly
            return describedPlace;//returns the object
        }
        if(placeType.equals("Named")){//else if the first placetype controll failed. This controll checks if the placetype is equals to the named.
            NamedPlace namedPlace = new NamedPlace(category, locationX, locationY, name);//Creates a namedPlace according to the
            setPlaceColor(namedPlace);//Sets the color of the place accordingly
            return namedPlace;//returns the object
        }
        return null;//if none of these controlls passed return null.
    }

    //Method to print out data in
    private void savePlaceText(PrintWriter out){//Passes in printwriter that allows the printing of data into a file in unicode format.
        for (Place p : byPosition.values()) {//Since each place has a unique position we are selecting the datastructure by position to aid us in acessing every single place objekt in the programm
            if (p instanceof DescribedPlace) {//if place is an instance of DescribedPlace the compiler proceeds with the block below.
                DescribedPlace d = (DescribedPlace)p;//Cast the object object to the desired class and proceed with the controls
                if(p.getCategory() == null) {//As previously mentioned, fif the palce has a null as category the format if the printout is changed accordingly to the control.
                    out.println("Described" + "," + "None" + "," + d.getPosition().getPositionx() + "," + d.getPosition().getPositiony() + "," + d.getName() + d.getDescription());
                }
                else{
                    out.println("Described" + "," + d.getCategory().name() + "," + d.getPosition().getPositionx() + "," + d.getPosition().getPositiony() + "," + d.getName() + "," + d.getDescription());
                    System.out.println("Described" + "," + d.getCategory().name() + "," + d.getPosition().getPositionx() + "," + d.getPosition().getPositiony() + "," + d.getName() + "," + d.getDescription());}

            } else if (p instanceof NamedPlace){//The same process is the repeated as the block above in the block below.
                if (p.getCategory() == null) {
                    out.println("Named" + "," + "None" + "," + p.getPosition().getPositionx() + "," + p.getPosition().getPositiony() + "," + p.getName());
                }
                else {
                    out.println("Named" + "," + p.getCategory().name() + "," + p.getPosition().getPositionx() + "," + p.getPosition().getPositiony() + "," + p.getName());
                    System.out.println("Named" + "," + p.getCategory().name() + "," + p.getPosition().getPositionx() + "," + p.getPosition().getPositiony() + "," + p.getName());
                }
            }
        }
    }

    private void createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);//Create a new alert of type information.
        alert.setHeaderText(message);//Sets content of the header text to the parameter.
        alert.showAndWait();//Displays the pop-up window.
    }

    private void removePlace(Place place){
        byName.get(place.getName()).remove(place);//Removes the place from the byName Map datastruture.
        byCategory.get(place.getCategory()).remove(place);//removes the place by it byName category.
        byPosition.remove(place.getPosition());//Removes the place from the ByPosition datastructure.
        centerPane.getChildren().remove(place);//remove the place from list of centerPanes.
    }

    private void placeImage(File file){
        String filename = file.getAbsolutePath();//with the file selected. We get its path saving it as a string.
        Image  image = new Image("file:" + filename);//together with the path string we then askt the new image object to find the image in the current folder.
        iv = new ImageView();//Creates a new ImageView();
        centerPane.getChildren().add(iv);//We add the newly created imageView and add it to the centerPane children.
        iv.setFitHeight(image.getHeight());//since we don't know the dimensions of the images to be loaded, we can just say to the imageView to adapt ts size the loaded image dimensions.
        iv.setFitWidth(image.getWidth());//same case as the height.
        iv.setImage(image);//Sets the image to the imageView
        changed = true;//Sets the state of the program to changed.
    }

    private void processSearch(Position position){
        Place foundPlace = byPosition.get(position);//in the byPosition Map. Search for a place with a Position object as key.
        foundPlace.setSelected(true);//Sets the selection mode of the place to true.
        foundPlace.setVisible(true);//Sets the visibility of the place to true.
        markedPlaces.add(foundPlace);//adds the found place to the list of marked places.
    }

    private void clearView(){//Method that clears the view port of all places, but retains the loaded image view.
        centerPane.getChildren().retainAll(iv);
        markedPlaces.clear();
        byName.clear();
        byCategory.clear();
        byPosition.clear();
    }

    private void createError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR, error);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private boolean controllChange(String message){//controls the state of the scene
        if(changed){//if at any state the program has been modified. proceeds with the block belo.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);//Displays an alert window.
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.CANCEL){
                return true;
            }
        }
        return false;
    }

    private void findPlace(CoordinateWindow cw){
        int locationX = cw.getLocationX();//get the position in the X location.
        int locationY = cw.getLocationy();//get the position in the Y location.
        Position positionLocation = new Position(locationX,locationY);//Since the Map to get the unique position re
        if(byPosition.get(positionLocation) == null){//IF the returned position is null exits.
            createAlert("No place in that location");//calls the create alert method.
            return;
        }else{
            if (!markedPlaces.isEmpty()) {//Checks the markedPlaces hashSet to if it is not empty.
                for (Place place : markedPlaces) {//Sets all the places in the hashSet.
                    place.setSelected(false);//Sets the selected place.
                }
            }
            processSearch(positionLocation);
        }
    }

    class NewButtonHandler implements EventHandler<ActionEvent>{//Method that calls a new method handler.
        public void handle(ActionEvent actionEvent){
            root.setCursor(Cursor.CROSSHAIR);//Sets the image of the cursor into a crosshair.
            centerPane.setOnMouseClicked(new ClickHandler());//Calls the handler whenever the "New" button has been pressed.
        }
    }

    class LoadHandler implements EventHandler<ActionEvent>{//
        @Override
        public void handle(ActionEvent event){//
            if(controllChange("Osparade ändringar, fortsätta ändå")){
                event.consume();
                return;
            }
            File file = fileChooser.showOpenDialog(primaryStage);//prompts the user with the open file window and sets the selected file int
            fileChooser.setTitle("Load Dialog");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));//Filters out objects that aren't of the type jpg or png.
            if(file == null)return;//if the file is exits the method inmediatly.
            //whenever we load a new image we want to get rid of all the places and nodes in the centerpane, thus we clear the viewport.
            clearView();
            placeImage(file);
        }
    }

    class SearchHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            try {
                for (Place place : markedPlaces) {//Acess the MarkedPlaces datastructure and its and sets the places to unselected.
                    place.setSelected(false);
                }
                markedPlaces.clear();//Just to be sure clears the list.
                Iterator<Place> it = byName.get(field.getText()).iterator();//Creates and iterator of set within the byName Map
                while (it.hasNext()) {//While the argument is true Proceed with block below
                    Place currentPlace = it.next();//Create a local variable to save the next item into.
                    currentPlace.setVisible(true);//Set the visibility of the current palce to blue;
                    currentPlace.setSelected(true);//Set selected mode of the place to true.
                    markedPlaces.add(currentPlace);//Adds the current place to the set of selected places.
                }
            }catch (Exception error){
                createError("Something wrong with formatting");
            }
        }
    }

    class CoordinatesHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            if(byPosition.isEmpty()){//Controll if the called list is selected else exit the data structure.
                createAlert("No Places in the viewport");
                return;//exits the handler.
            }
            try {
                CoordinateWindow cw = new CoordinateWindow();//if The controlls passed, proceed with the code below.
                Optional<ButtonType> answer = cw.showAndWait();// display the pop-up window and give the option to press ok or cancel.
                if(answer.isPresent() && answer.get() == ButtonType.CANCEL){
                    event.consume();
                    return;
                }
                findPlace(cw);
            }catch(NumberFormatException e){//if the field catches an ilegal argument display the error message on the screen.
                createError("Ej numerisk inmatning");
            }
        }
    }

    class ClickHandler implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent click){//Event is a mouseEvent
            int locationX = (int) click.getX();//Gets the X value of click Event. Since the requirements use whole values in the example my assumption is then to convert the double gotten from the click event into an integer.
            int locationY = (int) click.getY();//Gets the Y value of the clickEvent.
            Category category = getSelectedCategory();//returns the selected category in the listView and saves it in the category variable.
            if(described.isSelected()){//Depending on the type of radio button selected. Create a place accordingly.
                createDescribedPlace(category, locationX, locationY);
            }
            else if(named.isSelected()){
                createNamedPlace(category,locationX,locationY);
            }
            centerPane.setOnMouseClicked(null);
            changed = true;//Set the prgroam to changed after the place has been selected.
        }
    }

    class RemoveHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            Iterator<Place> it = markedPlaces.iterator();
            while(it.hasNext()){//Since we are modifying datastructures we should
                Place place = it.next();//Since we only can call next once to acces the next element.The variable next then contains the next eleement in the list.
                removePlace(place);
            }
        }
    }

    class HideHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            for(Place place : markedPlaces){//Change the state of the places and make sure the the modified objects are no longer present in the markedPlaces hashSet.
                place.setSelected(false);//
                place.setVisible(false);
            }
            markedPlaces.clear();
        }
    }

    class HideCategoryHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            if(getSelectedCategory() == null)return;
            Iterator<Place> it = byCategory.get(getSelectedCategory()).iterator();//Since we are accessing a hashset in a map and mofiying its elements. It is better to use an interator to avoid concurrent modification exceptions.
            while(it.hasNext()){//While the iterator has a next item proceed with the block below.
                Place currentPlace = it.next();//saves the next item in a variable as we can only call next once per item.
                currentPlace.setVisible(false);//sets the visibility of the node to false.
            }
        }
    }

    class ExitItemHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));//Whenver the window is closed through the X close button in the top right corner. Fire a window event
        }
    }

    class ExitHandler implements EventHandler<WindowEvent>{
        @Override
        public void handle(WindowEvent event){
            if(changed){//whenver the program has been changed under runtime check if wether the state of the program has been channged.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Osparade ändringar avsluta ändå?");//Crreate a new alert of type CONFIRMATION and display it on the on the screen.
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.CANCEL){//If the if user pressed cancel, consume the vent from proceeding further.
                    event.consume();
                }
            }
        }

    }

    class OpenHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            try{
                if(changed){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Osparade ändringar avsluta ändå?");//Whenever the user presses the Open item in the menu create a new Alert of type CONFIRMATION.
                    Optional<ButtonType> result = alert.showAndWait();//display the message on the screen.
                    if(result.isPresent() && result.get() == ButtonType.CANCEL){//Controll the answer of the user.
                        event.consume();//consumes the event.
                        return;
                    }
                }
                FileChooser fileChooser = new FileChooser();//Creates a new FileChooser object.
                File file = fileChooser.showOpenDialog(null);//Set a window with no predefined path.
                if(file == null){//If no file was opened exit the method.
                    return;
                }
                clearView();//else if a file was opened procedd to clear the view from items.
                String fileName = file.getAbsolutePath();//gets the path of the file in the form of as string.
                FileReader infile = new FileReader(fileName);//gets the path of the file and sets it as the parameter for filereader.
                BufferedReader in = new BufferedReader(infile);//Reads every line in the infile file reader.
                String line;
                while((line = in.readLine()) != null){//While the line contains elements proceed with the block.
                    String[] tokens = line.split(",");// save the tokens separated by a coma character.
                    createLoadedPlace(tokens);//send the token to the create loaded Place method.
                }
                changed = false;//sets the state of the program to not changed.
            }catch (FileNotFoundException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kan inte öppna filen" + e.getMessage());// if no file was found display an alert
                alert.showAndWait();
            }catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());//if any issues with the input and outputstreams are found display an alert.
                alert.showAndWait();
            }

        }
    }

    class SaveHandler implements EventHandler<ActionEvent>{//Handler to save the information of the program into a file.
        @Override
        public void handle(ActionEvent event){
            try {
                FileChooser fileChooser = new FileChooser();//Create a filechooser object
                File file = fileChooser.showSaveDialog(null);//Initiate the path as null
                if(file == null){// if no file selected exit the handler.
                    return;
                }
                String fileName = file.getAbsolutePath();
                FileWriter outFile = new FileWriter(fileName);
                PrintWriter out = new PrintWriter(outFile);
                if(file != null) {//if the file is not null save the contents of the program into said file.
                    savePlaceText(out);//print the lines into a txt file
                }
                changed = false;//sets the state of the program to not changed.
                out.close();//close the data stream
                outFile.close();//close the printout
            }catch(FileNotFoundException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kan inte öppna filen" + e.getMessage());// chekc for errors
                alert.showAndWait();

            }catch(IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());//Check for errors in the datastream.
                alert.showAndWait();
            }

        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
