/**
 * @author Angel Cardenas Martinez anca8079
 */

public class Appliance extends Valuable{
    private static final double WEAR_LEVEL = 10.0;
    private String name;
    private double price;
    private double wear;
   
    public Appliance(String name, double price, double wear) {
        super(name);
        this.price = price;
        this.wear = wear;
    }

    public double getWear(){
        return this.wear;
    }

    public double getPrice(){
        return this.price;
    }

    public double getValue(){
        return price * (wear/WEAR_LEVEL);
    }

    @Override
    public String toString(){
        return "[ " + "Name: " + super.getName() + ", Value with VAT: " + getValuePlusVAT() + ", värde: " + getValue() + ", inköpspris: " + getPrice() + ", skick: " + getWear() + " ]";
    }

}