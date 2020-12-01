/**
 * @author Angel Cardenas Martinez anca8079
 */

public abstract class Valuable{
    private String name;
    private double vat;
    private final double defaultVAT = 0.25;

    public Valuable(String name){
        this.name = name;
        this.vat = defaultVAT;

    }

    public String getName(){
        return this.name;
    }

    public abstract double getValue();

    public void setVAT(double newVAT){
        this.vat = newVAT;
    }
    public double getValuePlusVAT(){
        return this.getValue() + this.getValue() * vat;
    }

    public String toString(){
        return this.name;
    }

} 