/**
 * @author Angel Cardenas Martinez anca8079
 */



public class Jewellery extends Valuable{

    private String name;
    private int jewels;
    private final int singleJewel = 500;
    private final Material material;

    enum Material{
        Silver(700), Guld(2000);

        private final int value;

        Material(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Jewellery(String name, int jewels, String material){
        super(name);
        this.material = Material.valueOf(material);
        this.jewels = jewels;
    }

    public int getJewels(){
        return this.jewels;
    }
    @Override
    public double getValue() {
        return (double)(jewels * singleJewel + material.getValue());
    }

    public Material getMaterial(){
        return this.material;
    }

    @Override
    public String toString(){
        return  "[ " + "Name" + super.getName() + ", Value with VAT: " + getValuePlusVAT() + ", av: " + this.getMaterial() + ", antal ädelstenar: " + this.getJewels() + ", värde: " + getValue()  + " ]";
    }

}