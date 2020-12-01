/**
 * @author Angel Cardenas Martinez anca8079
 */

public class Stock extends Valuable{
    private String name;
    private int quantity;
    private double rate;

    public Stock(String name, int quantity, double rate){
        super(name);
        this.quantity = quantity;
        this.rate = rate;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getRate(){
        return this.rate;
    }

    public void setRate(int rate){
        this.rate = rate;
    }

    @Override
    public double getValue() {
        return this.rate * (double)this.quantity;
    }


    @Override
    public String toString(){
        return "[ " + "Name: " + super.getName() + ", Value with VAT" + getValuePlusVAT() + ", värde: " + getValue()  + ", antal: " + getQuantity() + ", kurs: " + getRate() + " ]";
    }
}