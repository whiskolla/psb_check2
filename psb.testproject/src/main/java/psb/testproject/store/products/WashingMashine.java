package psb.testproject.store.products;

import java.util.Date;

public class WashingMashine extends Product {
    boolean ifDryer;

    public WashingMashine(String name, double weight, double price, boolean ifDryer){
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.ifDryer = ifDryer;
    }

    @Override
    public String toString(){
         return "\nНазвание = " + name +
                ";\n наличие сушилки = " + ifDryer +
                ";\n вес = " + weight +
                ";\n цена = " + price;
    }

    public boolean isIfDryer() {
        return ifDryer;
    }
}
