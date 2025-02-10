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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Сравниваем ссылки на один и тот же объект
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Проверяем null и тип объекта
        }
        WashingMashine product = (WashingMashine) obj;
        return Double.compare(product.weight, weight) == 0 &&
                Double.compare(product.price, price) == 0 &&
                Boolean.compare(product.ifDryer, ifDryer) == 0 &&
                name.equals(product.name);
    }
}
