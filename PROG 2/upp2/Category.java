/**
 * @author Angel Cardenas Martinez anca8079
 */
public enum Category {
    Bus, Train, Underground;// Making use of Enums in order to statically control the behavior of other methods

    //Reads the argument and depending on the passed value a category type is returned.
    public static Category parseCategory(String string) {
        if(string.equals("None"))return null;
        if (string.equals("Bus")) return Category.Bus;
        else if (string.equals("Train")) return Category.Train;
        else if (string.equals("Underground")) return Category.Underground;
        return null;// in case all the other controls fails, returns null anyways.
    }
} 
